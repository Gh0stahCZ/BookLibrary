package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.BookDetailRepository
import com.tomaschlapek.booklibrary.model.BookDetail
import io.reactivex.Single
import javax.inject.Inject


class LoadBookDetailUseCase @Inject constructor(private val repo: BookDetailRepository) {

  fun execute(): Single<BookDetail> {
    return repo.bookDetail
  }
}