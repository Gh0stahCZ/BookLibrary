package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.IBookAddRepository
import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject


class AddBookUseCase @Inject constructor(private val repo: IBookAddRepository) {

  fun execute(newBook: AddBookBody): Single<Response<BookDetail>> {
    return repo.addBook(newBook)
  }
}