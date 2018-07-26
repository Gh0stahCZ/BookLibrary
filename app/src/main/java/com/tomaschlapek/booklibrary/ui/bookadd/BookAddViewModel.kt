package com.tomaschlapek.booklibrary.ui.bookadd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaschlapek.booklibrary.domain.interactor.AddBookUseCase
import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class BookAddViewModel(val sched: SchedulersFacade, val usecase: AddBookUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Data<BookDetail>>()

  init {
    Timber.i("Hello from Add Book ViewModel")
  }

  fun addBookDetail(newBook: AddBookBody) {
    addBookDetail(usecase, newBook)
  }

  private fun addBookDetail(usecase: AddBookUseCase, newBook: AddBookBody) {
    disposables.add(usecase.execute(newBook)
      .observeOn(sched.ui())
      .subscribeOn(sched.io())
      .doOnSubscribe { response.setValue(Data(dataState = DataState.LOADING, data = response.value?.data, message = null)) }
      .subscribe(
        { response ->
          if (response.isSuccessful) {
            this.response.setValue(Data(dataState = DataState.SUCCESS, data = response.body(), message = null))
          } else {
            this.response.setValue(Data(dataState = DataState.ERROR, data = null, message = response.errorBody()?.string()))
          }
        },
        { throwable -> response.setValue(Data(dataState = DataState.ERROR, data = response.value?.data, message = throwable.localizedMessage)) }
      )
    )
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }

}
