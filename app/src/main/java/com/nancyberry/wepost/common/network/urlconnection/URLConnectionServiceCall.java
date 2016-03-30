package com.nancyberry.wepost.common.network.urlconnection;

import com.nancyberry.wepost.common.network.AbstractServiceCall;
import com.nancyberry.wepost.common.network.IServiceResponse;
import com.nancyberry.wepost.common.network.PlusRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * BaseServiceCall focus on parsing the PlusRequest info and do the HTTP Request through the
 * HTTPURLConnection
 *
 * @author zhaonan
 * @version %I%, %G%
 */
public abstract class URLConnectionServiceCall extends AbstractServiceCall {
    @Override
    public IServiceResponse getResponse(PlusRequest plusrequest) throws IOException {
        URL url = new URL(plusrequest.getFullUrl());

        //HttpURLConnection default using GZIP
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(plusrequest.getReadTimeOut());
        urlConnection.setConnectTimeout(plusrequest.getConnectTimeOut());

        // Disable cache for all requests for now.
        urlConnection.setUseCaches(false);

        // Handling Request Headers
        Map<String, String> headerParams = plusrequest.getHeaderParams();
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // Handling POST Request
        if (plusrequest.getPostBody() != null) {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            byte[] postBody = plusrequest.getPostBody();

            urlConnection.setFixedLengthStreamingMode(postBody.length);

            OutputStream os = urlConnection.getOutputStream();
            os.write(postBody);
            os.flush();
            os.close();
        } else {
            if (plusrequest.shouldForcePost()) {
                urlConnection.setRequestMethod("POST");
            } else {
                urlConnection.setRequestMethod("GET");
            }
            urlConnection.setDoOutput(false);
        }

        return new URLConnectionHttpResponse(urlConnection);
    }
}
