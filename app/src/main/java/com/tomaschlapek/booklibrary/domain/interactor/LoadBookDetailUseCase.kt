package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.IBookDetailRepository
import com.tomaschlapek.booklibrary.model.BookDetail
import io.reactivex.Single
import javax.inject.Inject


class LoadBookDetailUseCase @Inject constructor(private val repo: IBookDetailRepository) {

  fun execute(): Single<BookDetail> {
    return repo.getDetail()
  }
}