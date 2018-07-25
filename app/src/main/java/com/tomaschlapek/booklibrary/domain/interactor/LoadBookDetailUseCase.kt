package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.BookDetailRepository
import io.reactivex.Single
import javax.inject.Inject


class LoadBookDetailUseCase @Inject constructor(private val greetingRepository: BookDetailRepository) {

  fun execute(): Single<String> {
    return greetingRepository.greeting
  }
}