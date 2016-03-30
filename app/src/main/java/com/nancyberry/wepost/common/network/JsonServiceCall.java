package com.nancyberry.wepost.common.network;

import com.nancyberry.wepost.common.util.IOUtils;

import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by nan.zhang on 3/30/16.
 */
public abstract class JsonServiceCall<ResultT> extends HttpServiceCall<ResultT> {

    public JsonServiceCall(PlusRequest request, IExecutor executor) {
        super(request, executor);
    }

    @Override
    protected void callSuccessHandlers(IServiceResponse response) {
        try {
            Object jsonValue = (new JSONTokener(getResponseString(response))).nextValue();
            ResultT result = processJson(jsonValue);

            if (successHandlers != null) {
                for (ServiceSuccessHandler successHandler : successHandlers) {
                    successHandler.onSuccess(result);
                }
            }

        } catch (Exception e) {
            callFailureHandlers(e);
        } finally {
            consumeResponse();
        }
    }


    protected abstract ResultT processJson(Object jsonValue) throws Exception;

    public String getResponseString(IServiceResponse response) throws IOException {
        return new String(IOUtils.toByteArray(response.getStream()), "UTF-8");
    }

}
