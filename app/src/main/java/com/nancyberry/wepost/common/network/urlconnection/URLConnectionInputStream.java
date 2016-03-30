package com.nancyberry.wepost.common.network.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by yicheng.liu on 2/2/16.
 */
public class URLConnectionInputStream extends InputStream {
    private InputStream inputStream;
    private HttpURLConnection urlConnection;

    protected URLConnectionInputStream(InputStream inputStream, HttpURLConnection urlConnection) {
        this.inputStream = inputStream;
        this.urlConnection = urlConnection;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
