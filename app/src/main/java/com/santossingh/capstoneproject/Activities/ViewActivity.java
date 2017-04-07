package com.santossingh.capstoneproject.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.santossingh.capstoneproject.R;

public class ViewActivity extends AppCompatActivity {

    private String id = "";
    private WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Bundle intent = getIntent().getExtras();
        id = intent.getString(String.valueOf(R.string.BOOK_ID));
        webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:initialize('" + id + "')");
            }
        });

        webview.loadUrl(getString(R.string.GOOGLE_PREVIEW_HTML));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}