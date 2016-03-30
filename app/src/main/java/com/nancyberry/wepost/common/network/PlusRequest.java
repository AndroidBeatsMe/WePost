package com.nancyberry.wepost.common.network;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request class which is used for packaging ALL metadata used for a HTTP Request, including
 * headers, baseURL, path, get params, post params and so on.
 *
 * @version %I%, %G%
 */
public class PlusRequest {

    public static final String HEADER_HULU_PLATFORM = "HULU-PLATFORM";

    public static final String CONNECTED_NANO_TIME = "connectedNanoTime";

    public static final String REQUEST_NANO_TIME = "requestNanoTime";

    private static final String TAG = "PlusRequest";

    protected Map<String, String> getParams = null;

    protected Map<String, String> postParams = null;

    protected byte[] postBody = null;

    protected Map<String, String> headerParams = null;

    protected String baseUri = null;

    protected String path = null;

    protected boolean shouldForcePost = false;

    protected boolean acceptPartialContent = false;

    protected int readTimeOut = 10 * 1000;

    protected int connectTimeOut = 10 * 1000;

    /**
     * Constructor, by default, we will add header: HULU-PLATFORM: thorn/DASH_STANDALONE for all
     * requests
     * todo, figure out why we add this header to ALL request?
     */
    public PlusRequest() {
        addHeaderParam(PlusRequest.HEADER_HULU_PLATFORM, "thorn/" + "DASH_STANDALONE");
    }

    /**
     * Constructor with baseUri param
     *
     * @param baseUri String base URL for the request
     */
    public PlusRequest(String baseUri) {
        this();
        this.baseUri = baseUri;
    }

    public PlusRequest(String baseUri, String path) {
        this(baseUri);
        this.path = path;
    }

    public boolean shouldForcePost() {
        return shouldForcePost;
    }

    /**
     * Get read time out
     *
     * @return time out in milliseconds
     */
    public int getReadTimeOut() {
        return readTimeOut;
    }

    /**
     * Set read time out
     *
     * @param readTimeOut time out in milliseconds
     */
    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    /**
     * Get connect time out
     *
     * @return time out in milliseconds
     */
    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    /**
     * Set connect time out
     *
     * @param connectTimeOut time out in milliseconds
     */
    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public String getPath() {
        return path;
    }

    public PlusRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public Map<String, String> getGetParams() {
        if (getParams == null) {
            return null;
        }
        return Collections.unmodifiableMap(getParams);
    }

    public Map<String, String> getPostParams() {
        if (postParams == null) {
            return null;
        }
        return Collections.unmodifiableMap(postParams);
    }

    public PlusRequest addGetParams(Bundle options) {
        for (String key : options.keySet()) {
            addGetParam(key, options.getString(key));
        }
        return this;
    }

    public PlusRequest addPostParams(Bundle options) {
        for (String key : options.keySet()) {
            addPostParam(key, options.getString(key));
        }
        return this;
    }

    public PlusRequest addPostBody(byte[] body) {
        postBody = body;
        return this;
    }

    public PlusRequest addGetParam(String key, String value) {
        if (value == null) {
            return this;
        }
        if (getParams == null) {
            getParams = new HashMap<String, String>();
        }
        getParams.put(key, value);
        return this;
    }

    public PlusRequest addPostParam(String key, String value) {
        if (value == null) {
            return this;
        }
        if (postParams == null) {
            postParams = new HashMap<String, String>();
        }
        postParams.put(key, value);
        return this;
    }

    public boolean getAcceptPartialContent() {
        return acceptPartialContent;
    }

    public PlusRequest setAcceptPartialContent(boolean value) {
        acceptPartialContent = value;
        return this;
    }

    public PlusRequest addHeaderParam(String key, String value) {
        if (value == null) {
            return this;
        }
        if (headerParams == null) {
            headerParams = new HashMap<String, String>();
        }
        headerParams.put(key, value);
        return this;
    }

    public byte[] getPostBody() {
        if (postParams != null || postBody != null) {
            try {
                if (postParams != null) {
                    return getQuery(postParams);
                } else {
                    return postBody;
                }
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return null;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    private byte[] getQuery(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString().getBytes("UTF-8");
    }

    public List<String> getSortedGetKeys() {
        if (getParams != null) {
            List<String> sortedKeys = new ArrayList<String>(getParams.keySet().size());
            sortedKeys.addAll(getParams.keySet());
            Collections.sort(sortedKeys);
            return sortedKeys;
        }
        return new ArrayList<String>(0);
    }

    public List<String> getSortedPostKeys() {
        if (postParams != null) {
            List<String> sortedKeys = new ArrayList<String>(postParams.keySet().size());
            sortedKeys.addAll(postParams.keySet());
            Collections.sort(sortedKeys);
            return sortedKeys;
        }
        return new ArrayList<String>(0);
    }

    public String getFullUrl() {
        Uri.Builder builder = Uri.parse(baseUri).buildUpon();

        if (path != null) {
            String p = path;
            if (path.startsWith("/")) {
                p = path.substring(1);
            }
            builder.appendEncodedPath(p);
        }

        for (String key : getSortedGetKeys()) {
            builder.appendQueryParameter(key, getParams.get(key));
        }

        return builder.build().toString();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getClass().getSimpleName());
        result.append(":");

        String fullUrl = getFullUrl();

        if (postParams != null) {
            result.append("POST - ");
            result.append(fullUrl);
            result.append("\n");
            boolean isFirst = true;

            for (String key : getSortedPostKeys()) {
                if (!isFirst) {
                    result.append(", ");
                } else {
                    isFirst = false;
                }

                result.append(key);
                result.append(": ");

                if (key.equalsIgnoreCase("password") || key
                    .equalsIgnoreCase("password_confirmation")) {
                    result.append("REDACTED");
                } else {
                    result.append(postParams.get(key));
                }
            }
        } else {
            if (shouldForcePost) {
                result.append("POST - ");
            } else {
                result.append("GET - ");
            }
            result.append(fullUrl);
        }

        return result.toString();
    }

    public PlusRequest forcePost() {
        shouldForcePost = true;
        return this;
    }

//    public HttpUriRequest toHttpUriRequest() throws IOException {
//        String fullUrl = getFullUrl();
//
//        if (postParams != null || postBody != null) {
//            HttpPost result = new HttpPost(fullUrl);
//            if (postParams != null) {
//                List<NameValuePair> list = new ArrayList<NameValuePair>();
//
//                for (Map.Entry<String, String> entry : postParams.entrySet()) {
//                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//                }
//
//                UrlEncodedFormEntity entity;
//                try {
//                    entity = new UrlEncodedFormEntity(list, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    throw new IOException("CLIENT UNABLE TO FORM POST PARAMS");
//                }
//                result.setEntity(entity);
//            } else {
//                ByteArrayEntity entity = new ByteArrayEntity(postBody);
//                result.setEntity(entity);
//            }
//
//            if (headerParams != null) {
//                for (Map.Entry<String, String> entry : headerParams.entrySet()) {
//                    result.addHeader(entry.getKey(), entry.getValue());
//                }
//            }
//
//            return result;
//        } else {
//            HttpUriRequest request;
//            if (shouldForcePost) {
//                request = new HttpPost(fullUrl);
//            } else {
//                request = new HttpGet(fullUrl);
//            }
//            if (headerParams != null) {
//                for (Map.Entry<String, String> entry : headerParams.entrySet()) {
//                    request.addHeader(entry.getKey(), entry.getValue());
//                }
//            }
//            return request;
//        }
//    }
}
