package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.santossingh.capstoneproject.R;

public class AmazonActivity extends AppCompatActivity {

    private static final String url = "file:///android_asset/google_preview.html";
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        Intent intent = getIntent();
        String Buy_link = intent.getStringExtra(String.valueOf(R.string.BUY_Amazon));
//        Toast.makeText(this, Buy_link, Toast.LENGTH_LONG).show();
        webview = (WebView) findViewById(R.id.webView_Amazon);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.loadUrl(Buy_link);
    }

}
