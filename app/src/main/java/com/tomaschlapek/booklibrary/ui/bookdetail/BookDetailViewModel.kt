package com.tomaschlapek.booklibrary.ui.bookdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.LoadBookDetailUseCase
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class BookDetailViewModel(val picasso: Picasso, val schedulersFacade: SchedulersFacade, val loadBookDetailUseCase: LoadBookDetailUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Data<BookDetail>>()

  init {
    Timber.i("Hello from Book Detail ViewModel")
    loadBookDetail()
  }

  fun loadBookDetail() {
    loadBookDetail(loadBookDetailUseCase)
  }

  private fun loadBookDetail(loadBookDetailUseCase: LoadBookDetailUseCase) {
    disposables.add(loadBookDetailUseCase.execute()
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
