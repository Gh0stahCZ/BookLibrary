package com.tomaschlapek.booklibrary.domain.repository

import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.network.BookApiService
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class LibraryRepository @Inject constructor(val retrofit: Retrofit) : ILibraryRepository {
  val libraryItem: Single<Response<List<BookItem>>>
    get() = Single.just(Response.success(listOf(
      BookItem(1, "/api/v1/items/8", "Title 1"),
      BookItem(2, "/api/v1/items/8", "Title 2"),
      BookItem(3, "/api/v1/items/8", "Title 3"),
      BookItem(4, "/api/v1/items/8", "Title 4")
    )))

  override fun getItems(page: Int, limit: Int) =
    retrofit.create(BookApiService::class.java)
      .getBookLibrary(page, limit)
}

interface ILibraryRepository {
  fun getItems(page: Int, limit: Int): Single<Response<ArrayList<BookItem>>>
}
