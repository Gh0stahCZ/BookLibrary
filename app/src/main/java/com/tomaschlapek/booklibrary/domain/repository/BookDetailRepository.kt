package com.tomaschlapek.booklibrary.domain.repository

import io.reactivex.Single
import javax.inject.Inject

class BookDetailRepository @Inject constructor() {
  val greeting: Single<String>
    get() = Single.just("Hello from BookDetailRepository")
}