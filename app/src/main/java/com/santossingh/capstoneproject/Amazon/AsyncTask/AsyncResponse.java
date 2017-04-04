package com.santossingh.capstoneproject.Amazon.AsyncTask;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;

import java.util.List;

/**
 * Created by Stark on 05/09/2016.
 */
public interface AsyncResponse {
    void processFinish(List<AmazonBook> result);
}
