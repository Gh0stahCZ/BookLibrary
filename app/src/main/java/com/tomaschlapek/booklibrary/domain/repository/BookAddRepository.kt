package com.tomaschlapek.booklibrary.domain.repository

import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.network.BookApiService
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class BookAddRepository @Inject constructor(val retrofit: Retrofit) : IBookAddRepository {
  override fun addBook(newBook: AddBookBody) =
    retrofit.create(BookApiService::class.java)
      .addBook(newBook)
}

interface IBookAddRepository {
  fun addBook(newBook: AddBookBody): Single<Response<BookDetail>>
}
