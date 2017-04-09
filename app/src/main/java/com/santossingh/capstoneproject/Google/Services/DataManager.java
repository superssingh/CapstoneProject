package com.santossingh.capstoneproject.Google.Services;

import com.santossingh.capstoneproject.Google.Interfaces.LibraryAPI;
import com.santossingh.capstoneproject.Google.Interfaces.LibraryService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager implements LibraryService {
    private static final String google_books_domain = "https://www.googleapis.com";
    private LibraryAPI BooksInfo;

    public void RetrofitOutput() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(google_books_domain)
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
