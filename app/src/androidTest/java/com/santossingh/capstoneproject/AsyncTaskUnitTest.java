package com.santossingh.capstoneproject;

import com.santossingh.capstoneproject.Amazon.AsyncTask.AsyncResponse;
import com.santossingh.capstoneproject.Amazon.AsyncTask.MyAsyncTask;
import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by santoshsingh on 04/04/17.
 */

public class AsyncTaskUnitTest extends TestCase {
    private final String key = "test";
    List<AmazonBook> books;
    boolean called;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAsyncTask() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        try {
            new MyAsyncTask(new AsyncResponse() {
                @Override
                public void processFinish(List<AmazonBook> result) {
                    books = result;
                    called = true;
                    signal.countDown();
                }
            }).execute(key);
            signal.await(10, TimeUnit.SECONDS);
            assertTrue(called);
            assertNotNull("Null Books", books);
            assertFalse("Books List Empty", books.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }
}
