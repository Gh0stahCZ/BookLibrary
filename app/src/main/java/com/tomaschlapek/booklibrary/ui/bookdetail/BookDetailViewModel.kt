package com.tomaschlapek.booklibrary.ui.bookdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaschlapek.booklibrary.domain.interactor.LoadBookDetailUseCase
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import com.tomaschlapek.booklibrary.util.createErrorResponse
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class BookDetailViewModel(val sched: SchedulersFacade, val usecase: LoadBookDetailUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Data<BookDetail>>()

  init {
    Timber.i("Hello from Book Detail ViewModel")
  }

  fun loadBookDetail(bookId: Int) {
    loadBookDetail(usecase, bookId)
  }

  private fun loadBookDetail(usecase: LoadBookDetailUseCase, bookId: Int) {
    disposables.add(usecase.execute(bookId)
      .observeOn(sched.ui())
      .subscribeOn(sched.io())
      .doOnSubscribe { _ -> response.setValue(Data(dataState = DataState.LOADING, data = null, message = null)) }
      .onErrorResumeNext { createErrorResponse(it) }
      //      .delay(3L, TimeUnit.SECONDS, sched.ui())
      .subscribe(
        { response ->
          if (response.isSuccessful) {
            this.response.setValue(Data(dataState = DataState.SUCCESS, data = response.body(), message = null))
          } else {
            this.response.setValue(Data(dataState = DataState.ERROR, data = null, message = response.errorBody()?.string()))
          }
        },
        { throwable -> response.setValue(Data(dataState = DataState.ERROR, data = null, message = throwable.localizedMessage)) }
      )
    )
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }

}
