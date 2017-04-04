package com.santossingh.capstoneproject.Google.Interfaces;

import com.santossingh.capstoneproject.Google.Models.BooksLibrary;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by santoshsingh on 24/12/16.
 */
public interface LibraryAPI {
    String free = "https://www.googleapis.com/books/v1/volumes?q=1&filter=free-ebooks";

    //Free Books
    @GET("/books/v1/volumes?q=fiction&filter=free-ebooks&maxResults=40")
    Call<BooksLibrary> getFreeFictionBooks();

    //Free Books
    @GET("/books/v1/volumes?q=nonfiction&filter=free-ebooks&maxResults=40")
    Call<BooksLibrary> getFreeNonFictionBooks();

    @GET("/books/v1/volumes?q=business&filter=free-ebooks&maxResults=40")
    Call<BooksLibrary> getFreeBusinessBooks();

    @GET("/books/v1/volumes?q=romance&filter=free-ebooks&maxResults=40")
    Call<BooksLibrary> getFreeRomanceBooks();

    @GET("/books/v1/volumes?q=fantasy&filter=free-ebooks&maxResults=40")
    Call<BooksLibrary> getFreeFantasyBooks();

}