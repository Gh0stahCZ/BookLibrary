package com.tomaschlapek.booklibrary.network

import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.BookItem
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface BookApiService {

  @GET("/api/v1/items/{id}")
  fun getBookDetail(@Path("id") id: Int): Single<Response<BookDetail>>

  @GET("/api/v1/items")
  fun getBookLibrary(@Query("page") page: Int, @Query("limit") limit: Int): Single<Response<List<BookItem>>>

  @POST("/api/v1/items")
  fun addBook(@Body newBook: AddBookBody): Single<Response<BookDetail>>

}
