package com.tomaschlapek.booklibrary.domain.repository

import com.tomaschlapek.booklibrary.model.BookItem
import io.reactivex.Single
import javax.inject.Inject

class LibraryRepository @Inject constructor() {
  val libraryItem: Single<List<BookItem>>
    get() = Single.just(mutableListOf(
      BookItem(1, "/api/v1/items/8", "Title 1"),
      BookItem(2, "/api/v1/items/8", "Title 2"),
      BookItem(3, "/api/v1/items/8", "Title 3"),
      BookItem(4, "/api/v1/items/8", "Title 4")
    ))
}