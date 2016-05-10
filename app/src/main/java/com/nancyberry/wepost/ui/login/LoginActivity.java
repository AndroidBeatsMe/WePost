package com.nancyberry.wepost.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nancyberry.wepost.R;
import com.nancyberry.wepost.common.context.GlobalContext;
import com.nancyberry.wepost.common.util.HttpUtils;
import com.nancyberry.wepost.sina.Http;
import com.nancyberry.wepost.sina.request.params.GetAccessTokenReqParams;
import com.nancyberry.wepost.support.model.AccessToken;

import butterknife.Bind;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nan.zhang on 3/28/16.
 */
public class LoginActivity extends Activity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.login_webview)
    WebView webView;

    public static final String AUTH_URI = String.format(
            "https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s",
            GlobalContext.getInstance().CLIENT_ID, GlobalContext.getInstance().REDIRECT_URI);

    public static final String BUNDLE_ACCESS_TOKEN = "access_token";

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView = (WebView) findViewById(R.id.login_webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    // must be enabled if the page contains js
        settings.setDomStorageEnabled(true);
//        settings.setAppCacheEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");

        String weiboCookies = CookieManager.getInstance().getCookie("https://api.weibo.com");
        Log.d(TAG, "Cookies from https://api.weibo.com: " + weiboCookies);
        // clear cache and cookie
        webView.clearCache(true);
        webView.clearHistory();
        clearCookies(this);

        Log.d(TAG, "load auth url = " + AUTH_URI);
        webView.loadUrl(AUTH_URI);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {   // this function will be called when user clicks a link
                Log.d(TAG, "url = " + url);
                String code = url.substring(url.indexOf("code") + 5);
                Log.d(TAG, "code = " + code);
                view.loadUrl(url);

                GetAccessTokenReqParams params = new GetAccessTokenReqParams()
                        .withClientId(GlobalContext.getInstance().CLIENT_ID)
                        .withClientSecret(GlobalContext.getInstance().CLIENT_SECRET)
                        .withGrantType("authorization_code")
                        .withCode(code)
                        .withRedirectUri(GlobalContext.getInstance().REDIRECT_URI);

                mSubscription = Http.getSinaApi()
                        .getAccessToken(HttpUtils.pojoToMap(params))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<AccessToken>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "getAccessToken completed!");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "getAccessToken failed!");
                                Toast.makeText(LoginActivity.this, "getAccessToken failed!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(AccessToken accessToken) {
                                Log.d(TAG, "getAccessToken onNext called! " + accessToken);
                                Toast.makeText(LoginActivity.this, "getAccessToken succeeded!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent();
                                intent.putExtra(BUNDLE_ACCESS_TOKEN, accessToken);
                                setResult(Activity.RESULT_OK, intent);
                                LoginActivity.this.finish();
                            }
                        });


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
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
