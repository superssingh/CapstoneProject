package com.santossingh.capstoneproject.amazon.async_task;

import com.santossingh.capstoneproject.amazon.models.AmazonBook;

import java.util.List;

public interface AsyncResponse {
    void processFinish(List<AmazonBook> result);
}
