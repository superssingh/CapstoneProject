package com.santossingh.capstoneproject.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.santossingh.capstoneproject.R;

public class AmazonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        Intent intent = getIntent();
        String Link = intent.getStringExtra(String.valueOf(R.string.URL_Link));
        showWebLink(Link);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showWebLink(String url) {
        WebView webview = (WebView) findViewById(R.id.webView_Amazon);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.loadUrl(url);
    }

}
