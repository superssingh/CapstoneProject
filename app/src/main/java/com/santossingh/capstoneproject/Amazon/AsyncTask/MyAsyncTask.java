package com.santossingh.capstoneproject.Amazon.AsyncTask;

import android.os.AsyncTask;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Amazon.Services.AWS_URL;
import com.santossingh.capstoneproject.Amazon.Services.MyXmlPullParser;
import com.santossingh.capstoneproject.R;

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
    protected List<AmazonBook> doInBackground(String... urls) {
        List<AmazonBook> booksList = new ArrayList<>();
        try {
            while (isCancelled()) {
                return null;
            }
            String url = new AWS_URL().getURLByCategory(urls[0]);
            MyXmlPullParser myXmlPullParser = new MyXmlPullParser();
            InputStream is = downloadUrl(url);
            booksList = myXmlPullParser.parse(is);
            return booksList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCancelled(List<AmazonBook> amazonBooks) {
        super.onCancelled(amazonBooks);
    }

    @Override
    protected void onPostExecute(List<AmazonBook> amazonBooksList) {
        listener.processFinish(amazonBooksList);
    }

    private InputStream downloadUrl(String urls) throws IOException {
        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(R.string.ReadTimeOutSecond /* milliseconds */);
        conn.setConnectTimeout(R.string.ConnectTimeOutSecond /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}