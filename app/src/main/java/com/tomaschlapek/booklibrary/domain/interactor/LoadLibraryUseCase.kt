package com.tomaschlapek.booklibrary.domain.interactor

import com.tomaschlapek.booklibrary.domain.repository.ILibraryRepository
import com.tomaschlapek.booklibrary.model.BookItem
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class LoadLibraryUseCase @Inject constructor(private val repo: ILibraryRepository) : ILoadLibraryUseCase {

  override fun execute(page: Int, limit: Int): Single<Response<ArrayList<BookItem>>> {
    return repo.getItems(page, limit)
  }
}

interface ILoadLibraryUseCase {
  fun execute(page: Int, limit: Int): Single<Response<ArrayList<BookItem>>>
}