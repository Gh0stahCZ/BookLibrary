package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.LoadLibraryUseCase
import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LibraryViewModel(var picasso: Picasso, val schedulersFacade: SchedulersFacade, val loadLibraryUseCase: LoadLibraryUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Data<List<BookItem>>>()

  init {
    Timber.i("Hello from Library ViewModel")
    loadBookDetail()
  }

  fun loadNextPage() {
    //TODO
  }

  fun onBookClick(book: BookItem) {
    loadBookDetail()
  }

  fun loadBookDetail() {
    loadLibrary(loadLibraryUseCase)
  }

  private fun loadLibrary(useCase: LoadLibraryUseCase) {
    disposables.add(useCase.execute()
      .observeOn(schedulersFacade.ui())
      .subscribeOn(schedulersFacade.io())
      .doOnSubscribe { disposable -> response.setValue(Data(dataState = DataState.LOADING, data = response.value?.data, message = null)) }
      .delay(3L, TimeUnit.SECONDS, schedulersFacade.ui())
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
