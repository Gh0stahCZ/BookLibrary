package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.IBookDetailRepository
import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import io.reactivex.Single
import javax.inject.Inject


class AddBookUseCase @Inject constructor(private val repo: IBookDetailRepository) {

  fun execute(newBook: AddBookBody): Single<BookDetail> {
    return repo.getDetail()
  }
}