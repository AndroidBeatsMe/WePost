package com.nancyberry.wepost.common.network;

/**
 * Interface for ServiceSuccessHandler used for HTTP successful callback.
 *
 * @version %I%, %G%
 */
public interface ServiceSuccessHandler<ResultT> {

    void onSuccess(ResultT result);
}
