package com.nancyberry.wepost.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nancyberry.wepost.R;

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

//    public static final String CLIENT_ID = "2943694874";
//    public static final String CLIENT_SECRET = "3520b6832639b685ddb29dd534048010";
//    public static final String REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";

    public static final String CLIENT_ID = "2362431378";
    public static final String CLIENT_SECRET = "582ce3cdcdeb8a3b45087073d0dbcadf";
    public static final String REDIRECT_URI = "http://boyqiang520.s8.csome.cn/oauth2/";

    public static final String AUTH_URI = String.format("https://api.weibo.com/oauth2/authorize" +
            "?client_id=%s&redirect_uri=%s", CLIENT_ID, REDIRECT_URI);
    public static final String ACCESS_TOKEN_URI = "https://api.weibo.com/oauth2/access_token";

    public static final String BUNDLE_ACCESS_TOKEN = "access_token";
    public static final String BUNDLE_EXPIRE_IN = "expires_in";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mWebView = (WebView) findViewById(R.id.login_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);    // must be enabled if the page contains js
        settings.setDomStorageEnabled(true);
//        settings.setAppCacheEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");

        String weiboCookies = CookieManager.getInstance().getCookie("https://api.weibo.com");
        Log.d(TAG, "Cookies from https://api.weibo.com: " + weiboCookies);
        // clear cache and cookie
        mWebView.clearCache(true);
        mWebView.clearHistory();
        clearCookies(this);

        Log.d(TAG, "load auth url = " + AUTH_URI);
        mWebView.loadUrl(AUTH_URI);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {   // this function will be called when user clicks a link
                Log.d(TAG, "url = " + url);
                String code = url.substring(url.indexOf("code") + 5);
                Log.d(TAG, "code = " + code);
                view.loadUrl(url);

                try {
                    String jsonData = new AccessTokenTask().execute(code).get();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String accessToken = jsonObject.getString("access_token");
                    Long expireIn = jsonObject.getLong("expires_in");
                    Log.d(TAG, "access_token = " + accessToken);

                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_ACCESS_TOKEN, accessToken);
                    intent.putExtra(BUNDLE_EXPIRE_IN, expireIn);
                    setResult(Activity.RESULT_OK, intent);
                    LoginActivity.this.finish();

                } catch (Exception ie) {
                    Log.e(TAG, ie.getMessage());
                }

                return true;
            }

        });

    }

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d(TAG, "Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            Log.d(TAG, "Using clearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
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
                return jsonData;

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }

}
