package com.nancyberry.wepost.common.network;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by yicheng.liu on 2/15/16.
 */
public abstract class AbstractServiceCall {

    protected static final String TAG = AbstractServiceCall.class.getSimpleName();

    protected static final int MAX_RETRY_COUNT = 3;

    protected int retryCount = MAX_RETRY_COUNT;

    protected ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);

    protected ScheduledFuture<?> timeoutFuture;

    public void consumeResponse(IServiceResponse response) {
        cancelTimeout();

        if (response != null) {
            response.consume();
        }
    }

    public void release() {
        cancelTimeout();
        scheduler.shutdownNow();
        scheduler = null;
    }

    protected void cancelTimeout() {
        if (timeoutFuture != null && scheduler != null) {
            timeoutFuture.cancel(true);
            timeoutFuture = null;
            scheduler.purge();
        }
    }

    protected boolean isBadRequest(PlusRequest plusrequest) {
        if (plusrequest == null) {
            return true;
        }
        if (TextUtils.isEmpty(plusrequest.getBaseUri())) {
            return true;
        }

        URI uri = URI.create(plusrequest.getFullUrl());
        if (uri == null || TextUtils.isEmpty(uri.getHost())) {
            return true;
        }

        return false;
    }

    /**
     * This will automatically retry if it comes across the following errors:
     * 1. Any IOException (connection timeout, bad route to host, etc)
     * 2. The HTTP errors in httpResponseShouldRetry()
     *
     * @param plusrequest plus request
     * @return IServiceResponse instance for the response
     * @throws IOException when request keep failing after retry
     */
    protected final IServiceResponse sendRequest(PlusRequest plusrequest) throws IOException {
        return sendRequest(plusrequest, retryCount);
    }

    /**
     * Send request with retry via HTTPURLConnection
     *
     * @param plusrequest plus request
     * @param retriesLeft retry count
     *
     * @return HttpURLConnection instance for the request
     * @throws IOException raised only when it won't retry the requests anymore.
     */
    private IServiceResponse sendRequest(PlusRequest plusrequest, int retriesLeft) throws IOException {
        if (isBadRequest(plusrequest)) {
            Log.e(TAG, "isBadRequest");
            return null;
        }

        IServiceResponse response = null;
        try {
            response = getResponse(plusrequest);
            if (response.isSuccessful()) {
                return response;
            } else {
                int code = response.getCode();

                consumeResponse(response);

                if (retriesLeft >= 0) {
                    if (code == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
                        Log.d(TAG, "got 408 retrying: " + retriesLeft);
                        return sendRequest(plusrequest, retriesLeft - 1);
                    } else if (code == HttpURLConnection.HTTP_UNAVAILABLE) {
                        // handle this https://wiki.hulu.com/display/CSERV/Circuit+Breaker
                        Log.d(TAG, "got 503");
                        String retryAfter = response.getHeader("Retry-After", null);
                        if (retryAfter != null) {
                            int secondsToWait = 0;

                            try {
                                secondsToWait = Integer.valueOf(retryAfter);
                            } catch (NumberFormatException e) {
                                // this means mozart messed up - this should be an integer representing seconds
                                secondsToWait = 0;
                            }

                            if (secondsToWait >= 0) {
                                try {
                                    Thread.sleep(secondsToWait * 1000);
                                } catch (InterruptedException e) {
                                    // ignore
                                    Log.e(TAG, e.toString());
                                }
                            }
                        }

                        return sendRequest(plusrequest, retriesLeft - 1);
                    } else {
                        Log.d(TAG, "got " + code);
                        return sendRequest(plusrequest, retriesLeft - 1);
                    }
                }
            }
        } catch (IOException e) {
            consumeResponse(response);

            //RICK-3160, RICK-3215. As described above.
            if (retriesLeft > 0) {
                return sendRequest(plusrequest, 0);
            } else {
                throw e;
            }
        }
        return null;
    }

    /**
     * set Retry Count of the request, otherwise the default value would be MAX_RETRY_COUNT
     *
     * @param count retry count
     *
     * @return this
     */
    public final AbstractServiceCall setRetryCount(int count) {
        retryCount = count;
        return this;
    }

    /**
     * The abstract method of execute()
     */
    public abstract void execute();

    /**
     * Fetch response for the given request
     *
     * Subclass can use different solution to implement this method.
     * Any response should be wrap into the interface {@link IServiceResponse}.
     *
     * @param plusRequest
     * @return An IServiceResponse returned by service
     * @throws IOException
     */
    protected abstract IServiceResponse getResponse(PlusRequest plusRequest) throws IOException;
}
