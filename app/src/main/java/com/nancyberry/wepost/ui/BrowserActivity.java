package com.nancyberry.wepost.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nancyberry.wepost.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nan.zhang on 5/13/16.
 */
public class BrowserActivity extends Activity {

    private final static String TAG = BrowserActivity.class.getSimpleName();

    public static final String BUNDLE_URL = BrowserActivity.class.getName() + ".BUNDLE_URL";

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

        progressBar.setIndeterminate(true);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        String url = null;
        String action = getIntent().getAction();
        if (Intent.ACTION_VIEW.equalsIgnoreCase(action) && getIntent().getData() != null) {
            url = getIntent().getData().toString();
        } else {
            // read from bundle
            url = getIntent().getParcelableExtra(BUNDLE_URL).toString();
        }

        if (url != null) {
            webview.loadUrl(url);
        }

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    view.loadUrl("http://" + url);
                } else {
                    view.loadUrl(url);
                }

                Log.d(TAG, "load: " + url);
                return true;
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }

    public static void actionStart(Context context, Uri url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BUNDLE_URL, url);
        context.startActivity(intent);
    }
}
