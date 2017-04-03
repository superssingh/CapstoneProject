package com.santossingh.capstoneproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.santossingh.capstoneproject.R;

public class AmazonActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        Intent intent = getIntent();
        String BuyLink = intent.getStringExtra(String.valueOf(R.string.BUY_Amazon));
        String ReviewsLink = intent.getStringExtra(String.valueOf(R.string.Review_Link));
        if (BuyLink != null) {
            showWebLink(BuyLink);
        } else {
            showWebLink(ReviewsLink);
        }

    }

    public void showWebLink(String url) {
        webview = (WebView) findViewById(R.id.webView_Amazon);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.loadUrl(url);
    }

}
