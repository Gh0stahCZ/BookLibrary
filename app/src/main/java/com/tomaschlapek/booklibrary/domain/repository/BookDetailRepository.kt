package com.tomaschlapek.booklibrary.domain.repository

import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.network.BookApiService
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class BookDetailRepository @Inject constructor(val retrofit: Retrofit) : IBookDetailRepository {
  val bookDetail: Single<Response<BookDetail>>
    get() = Single.just(Response.success(BookDetail(1, "https://ia802608.us.archive.org/zipview.php?zip=/8/items/olcovers189/olcovers189-L.zip&file=1893213-L.jpg", "Title", "Author", 15.5)))

  override fun getDetail(bookId: Int) =
    retrofit.create(BookApiService::class.java)
      .getBookDetail(bookId)

}

interface IBookDetailRepository {
  fun getDetail(bookId: Int): Single<Response<BookDetail>>
}
