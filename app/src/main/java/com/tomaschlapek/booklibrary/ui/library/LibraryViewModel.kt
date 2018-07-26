package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.ILoadLibraryUseCase
import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import com.tomaschlapek.booklibrary.util.LIBRARY_LIMIT
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LibraryViewModel(var picasso: Picasso, val sched: SchedulersFacade, val usecase: ILoadLibraryUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Data<List<BookItem>>>()

  init {
    Timber.i("Hello from Library ViewModel")
    loadLibrary()
  }

  fun loadNextPage() {
    //TODO
  }

  fun onBookClick(book: BookItem) {
    loadLibrary()
  }

  fun loadLibrary() {
    loadLibrary(usecase)
  }

  private fun loadLibrary(useCase: ILoadLibraryUseCase, page: Int = 0, limit: Int = LIBRARY_LIMIT) {
    disposables.add(useCase.execute(page * limit, limit)
      .observeOn(sched.ui())
      .subscribeOn(sched.io())
      .doOnSubscribe { disposable -> response.setValue(Data(dataState = DataState.LOADING, data = response.value?.data, message = null)) }
      .delay(3L, TimeUnit.SECONDS, sched.ui())
      .subscribe(
        { bookData -> response.setValue(Data(dataState = DataState.SUCCESS, data = bookData, message = null)) },
        { throwable -> response.setValue(Data(dataState = DataState.SUCCESS, data = response.value?.data, message = throwable.localizedMessage)) }
      )
    )
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }

}
