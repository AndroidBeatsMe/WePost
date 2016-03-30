package com.nancyberry.wepost.common.network;

import com.nancyberry.wepost.common.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Http class which returns byte array
 *
 * @author tao.li
 * @version %I%, %G%
 */
public final class ByteArrayHttpServiceCall extends HttpServiceCall<byte[]> {

    private static final String TAG = ByteArrayHttpServiceCall.class.getSimpleName();

    /**
     * Constructor for HttpServiceCall class
     *
     * @param request  PlusRequest which contains metadata for the HTTP request
     */
    public ByteArrayHttpServiceCall(PlusRequest request) {
        super(request, null);
    }

    /**
     * Constructor for HttpServiceCall class
     *
     * @param request  PlusRequest which contains metadata for the HTTP request
     * @param executor The exexutor used for executing HTTP request task
     */
    public ByteArrayHttpServiceCall(PlusRequest request, IExecutor executor) {
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
            byte[] data = IOUtils.toByteArray(inputStream);

            if (successHandlers != null) {
                for (ServiceSuccessHandler<byte[]> successHandler : successHandlers) {
                    successHandler.onSuccess(data);
                }
            }
        } catch (IOException e) {
            callFailureHandlers(e);
        } finally {
            consumeResponse();
        }
    }
}
