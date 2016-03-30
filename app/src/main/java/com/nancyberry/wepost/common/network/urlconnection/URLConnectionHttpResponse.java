package com.nancyberry.wepost.common.network.urlconnection;

import android.util.Log;

import com.nancyberry.wepost.common.network.IServiceResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by yicheng.liu on 2/15/16.
 */
public class URLConnectionHttpResponse implements IServiceResponse {

    private static final String TAG = URLConnectionHttpResponse.class.getSimpleName();

    private HttpURLConnection connection;

    protected URLConnectionHttpResponse(HttpURLConnection connection) {
        this.connection = connection;
    }

    @Override
    public InputStream getStream() throws IOException {
        return new URLConnectionInputStream(connection.getInputStream(), connection);
    }

    @Override
    public int getCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            return HttpURLConnection.HTTP_INTERNAL_ERROR;
        }
    }

    @Override
    public boolean isSuccessful() {
        return 200 <= getCode() && getCode() < 300;
    }

    @Override
    public String getHeader(String name, String defaultValue) {
        String value = connection.getHeaderField(name);

        return value != null ? value : defaultValue;
    }

    @Override
    public void consume() {
        if (connection == null) {
            return;
        }
        try {
            //Make sure we closed the stream to release the socket resource
            if (connection.getInputStream() != null) {
                connection.getInputStream().close();
            }
        } catch (IOException e) {
            Log.d(TAG,
                    "Got IOException when trying to close InputStream, just ignore. Details: " + e
                            .getMessage());
        }
        connection.disconnect();
    }
}
