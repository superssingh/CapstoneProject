package com.santossingh.capstoneproject.Amazon.AsyncTask;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;

import java.util.List;

public interface AsyncResponse {
    void processFinish(List<AmazonBook> result);
}
