package com.nancyberry.wepost.common.network;

import android.util.Log;

import com.nancyberry.wepost.common.network.urlconnection.URLConnectionServiceCall;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Wrapped with handlers
 *
 * @author tao.li
 * @version %I%, %G%
 */
public abstract class HttpServiceCall<ResultT> extends URLConnectionServiceCall {

    private final String TAG = HttpServiceCall.class.getSimpleName();

    protected List<ServiceSuccessHandler<ResultT>> successHandlers;

    protected List<ServiceFailureHandler> failureHandlers;

    protected IExecutor executor;

    protected Runnable requestTask;

    protected PlusRequest plusRequest = null;

    protected IServiceResponse httpResponse = null;

    /**
     * Constructor for HttpServiceCall class
     *
     * @param request  PlusRequest which contains metadata for the HTTP request
     * @param executor The executor used for executing HTTP request task
     */
    public HttpServiceCall(PlusRequest request, IExecutor executor) {
        plusRequest = request;
        this.requestTask = new Runnable() {
            @Override
            public void run() {
                try {
                    long requestTimestamp = System.nanoTime();
                    httpResponse = sendRequest(plusRequest);
                    long connectedTimestamp = System.nanoTime();
                    plusRequest.addGetParam(PlusRequest.REQUEST_NANO_TIME,
                            Long.toString(requestTimestamp));
                    plusRequest.addGetParam(PlusRequest.CONNECTED_NANO_TIME,
                            Long.toString(connectedTimestamp));
                    if (httpResponse == null) {
                        callFailureHandlers(new NullPointerException("Http Response is null!"));
                    } else {
                        callSuccessHandlers(httpResponse);
                    }
                } catch (Exception e) {
                    callFailureHandlers(e);
                } finally {
                    release();
                }
            }
        };
        this.executor = executor;
    }

    /**
     * Method used for adding ServiceSuccessHandler to the ServiceCall
     *
     * @param successHandler handler for success result
     * @return this
     */
    public final HttpServiceCall addOnSuccessHandler(ServiceSuccessHandler<ResultT> successHandler) {
        if (successHandlers == null) {
            successHandlers = new LinkedList<>();
        }
        successHandlers.add(successHandler);
        return this;
    }

    /**
     * Method used for adding ServiceFailureHandler to the ServiceCall
     *
     * @param failureHandler handler to deal with failure
     * @return this
     */
    public final HttpServiceCall addOnFailureHandler(ServiceFailureHandler failureHandler) {
        if (failureHandlers == null) {
            failureHandlers = new LinkedList<>();
        }
        failureHandlers.add(failureHandler);
        return this;
    }

    protected abstract void callSuccessHandlers(IServiceResponse response);

    /**
     * Method used for calling ServiceFailureHandler with InputStream, this method will ensure all
     * failureHandlers be called in order.
     *
     * @param e Exception which is gotten from HttpURLConnection
     */
    protected void callFailureHandlers(Exception e) {
        if (failureHandlers != null) {
            boolean handled = false;
            for (ServiceFailureHandler failureHandler : failureHandlers) {
                if (failureHandler != null) {
                    handled = failureHandler.onFailure(e) || handled;
                }
            }
            if (!handled) {
                Log.e(TAG, (e.getMessage() == null) ? e.toString() : e.getMessage());
            }
        }
    }

    /**
     * Execute the Http Request with Executor, if executor is null, we will just do blocking
     * execution.
     */
    public final void execute() {
        if (executor == null) {
            //Log.d(TAG, "Executor is null, do synchronized execution!");
            requestTask.run();
        } else {
            executor.execute(requestTask);
        }
    }

    public final Future<ResultT> queue() {
        final Holder<ResultT> holder = new Holder<>();

        this.addOnSuccessHandler(new ServiceSuccessHandler<ResultT>() {
            @Override
            public void onSuccess(ResultT result) {
                holder.set(result);
            }
        }).addOnFailureHandler(new ServiceFailureHandler() {
            @Override
            public boolean onFailure(Exception e) {
                Log.e(TAG,
                        "Got failure during http request with error " + e.toString() + " " + e
                                .getMessage());
                holder.set(null);
                return false;
            }
        }).execute();

        return holder;
    }

    protected final void consumeResponse() {
        consumeResponse(httpResponse);
    }

    /**
     * Interface used for inject customized task executor
     */
    public interface IExecutor {
        void execute(Runnable task);
    }
}

