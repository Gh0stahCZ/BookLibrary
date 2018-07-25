package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.LibraryRepository
import com.tomaschlapek.booklibrary.model.BookItem
import io.reactivex.Single
import javax.inject.Inject


class LoadLibraryUseCase @Inject constructor(private val libraryRepository: LibraryRepository) {

  fun execute(): Single<List<BookItem>> {
    return libraryRepository.libraryItem
  }
}