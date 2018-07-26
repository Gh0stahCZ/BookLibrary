package com.tomaschlapek.booklibrary.domain.repository

import com.tomaschlapek.booklibrary.model.BookDetail
import io.reactivex.Single
import javax.inject.Inject

class BookDetailRepository @Inject constructor() : IBookDetailRepository {
  val bookDetail: Single<BookDetail>
    get() = Single.just(BookDetail(1, "https://ia802608.us.archive.org/zipview.php?zip=/8/items/olcovers189/olcovers189-L.zip&file=1893213-L.jpg", "Title", "Author", 15.5))

  override fun getDetail() = bookDetail

}

interface IBookDetailRepository {
  fun getDetail(): Single<BookDetail>
}
