package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.BookDetailRepository
import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import io.reactivex.Single
import javax.inject.Inject


class AddBookUseCase @Inject constructor(private val repo: BookDetailRepository) {

  fun execute(newBook: AddBookBody): Single<BookDetail> {
    return repo.bookDetail
  }
}