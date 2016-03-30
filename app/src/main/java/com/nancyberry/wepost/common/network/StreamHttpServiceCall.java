package com.nancyberry.wepost.common.network;

import java.io.IOException;
import java.io.InputStream;

/**
 * Http class which returns stream
 *
 * @author tao.li
 * @version %I%, %G%
 */
public final class StreamHttpServiceCall extends HttpServiceCall<InputStream> {

    private final String TAG = "StreamHttpServiceCall";

    /**
     * Constructor with PlusRequest, executor is default to null
     *
     * @param request PlusRequest
     */
    public StreamHttpServiceCall(PlusRequest request) {
        this(request, null);
    }

    /**
     * Constructor with PlusRequest and Executor
     *
     * @param request  PlusRequest
     * @param executor Executor which is used for executing HTTP tasks
     */
    public StreamHttpServiceCall(PlusRequest request, IExecutor executor) {
        super(request, executor);
    }

    /**
     * Method used for calling ServiceSuccessHandler with InputStream, this method will ensure to
     * close and disconnect the HTTPConnection after received data is resolved.
     *
     * @param result IServiceResponse which is gotten from HttpURLConnection
     */
    protected void callSuccessHandlers(IServiceResponse result) {
        try {
            InputStream inputStream = result.getStream();

            if (successHandlers != null) {
                for (ServiceSuccessHandler<InputStream> successHandler : successHandlers) {
                    successHandler.onSuccess(inputStream);
                }
            }
        } catch (IOException e) {
            callFailureHandlers(e);
        } finally {
            // The result stream may be kept by successHandler for further usage
            // The closing matters should be taken care by the user of it
            // consumeResponse();
        }
    }
}
