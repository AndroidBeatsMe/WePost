package com.nancyberry.wepost.common.network;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yicheng.liu on 2/15/16.
 */
public interface IServiceResponse {
    /**
     * Get the input stream of the body of the response
     *
     * @return the input stream of the response body
     * @throws IOException
     */
    InputStream getStream() throws IOException;

    /**
     * The HTTP status code of the response
     *
     * @return the HTTP status code
     */
    int getCode();

    /**
     * Whether the response is a successful one
     *
     * @return
     */
    boolean isSuccessful();

    /**
     *
     * @param name of the header
     * @param defaultValue
     * @return
     */
    String getHeader(String name, String defaultValue);

    void consume();
}
