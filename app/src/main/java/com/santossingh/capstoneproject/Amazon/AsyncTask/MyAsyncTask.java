package com.santossingh.capstoneproject.Amazon.AsyncTask;

import android.os.AsyncTask;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Amazon.Services.AWS_URL;
import com.santossingh.capstoneproject.Amazon.Services.MyXmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAsyncTask extends AsyncTask<String, Void, List<AmazonBook>> {

    private AsyncResponse listener = null;

    public MyAsyncTask(AsyncResponse listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<AmazonBook> doInBackground(String... urls) {
        String url = new AWS_URL().getURLByCategory(urls[0]);
        List<AmazonBook> booksList = new ArrayList<>();
        try {
            MyXmlPullParser myXmlPullParser = new MyXmlPullParser();
            InputStream is = downloadUrl(url);
            booksList = myXmlPullParser.parse(is);
            if (isCancelled()) {
                booksList = null;
                return booksList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booksList;
    }

    @Override
    protected void onCancelled(List<AmazonBook> amazonBooks) {
        super.onCancelled(amazonBooks);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(List<AmazonBook> amazonBooksList) {
        listener.processFinish(amazonBooksList);
    }

    private InputStream downloadUrl(String urls) throws IOException {
        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}