package com.santossingh.capstoneproject.Google.Services;

import com.santossingh.capstoneproject.Google.Interfaces.LibraryAPI;
import com.santossingh.capstoneproject.Google.Interfaces.LibraryService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stark on 18/04/2016.
 */
public class DataManager implements LibraryService {
    private LibraryAPI BooksInfo;

    public void RetrofitOutput() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BooksInfo = retrofit.create(LibraryAPI.class);
    }

    @Override
    public LibraryAPI getJSONData() {
        if (BooksInfo == null) {
            RetrofitOutput();
        }
        return BooksInfo;
    }
}
