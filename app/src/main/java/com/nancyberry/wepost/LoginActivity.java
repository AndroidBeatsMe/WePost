package com.nancyberry.wepost;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nan.zhang on 3/28/16.
 */
public class LoginActivity extends Activity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    public static final String CLIENT_ID = "2943694874";
    public static final String CLIENT_SECRET = "3520b6832639b685ddb29dd534048010";
    public static final String REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";
    public static final String AUTH_URI = String.format("https://api.weibo.com/oauth2/authorize" +
            "?client_id=%s&redirect_uri=%s", CLIENT_ID, REDIRECT_URI);
    public static final String ACCESS_TOKEN_URI = "https://api.weibo.com/oauth2/access_token";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mWebView = (WebView) findViewById(R.id.login_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");

        mWebView.loadUrl(AUTH_URI);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                Log.d(TAG, "url = " + url);
                String code = url.substring(url.indexOf("code") + 5);
                Log.d(TAG, "code = " + code);
                view.loadUrl(url);
                AccessTokenTask accessTokenTask = new AccessTokenTask();
                accessTokenTask.execute(code);

                return true;
            }

        });

    }

    public class AccessTokenTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder().add("client_id", CLIENT_ID)
                    .add("client_secret", CLIENT_SECRET)
                    .add("grant_type", "authorization_code")
                    .add("code", params[0])
                    .add("redirect_uri", REDIRECT_URI).build();

            Request request = new Request.Builder()
                    .url(ACCESS_TOKEN_URI)
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                String access_token = jsonObject.getString("access_token");
                Log.d(TAG, jsonObject.toString());

                return access_token;

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }


}
