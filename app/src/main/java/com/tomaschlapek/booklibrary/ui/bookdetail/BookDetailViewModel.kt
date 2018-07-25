package com.tomaschlapek.booklibrary.ui.bookdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.LoadBookDetailUseCase
import com.tomaschlapek.booklibrary.model.Response
import com.tomaschlapek.booklibrary.model.ResponseError
import com.tomaschlapek.booklibrary.model.ResponseLoading
import com.tomaschlapek.booklibrary.model.ResponseSuccess
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class BookDetailViewModel(val picasso: Picasso, val schedulersFacade: SchedulersFacade, val loadBookDetailUseCase: LoadBookDetailUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Response>()

  init {
    Timber.i("Hello from Book Detail ViewModel")
  }

  fun loadBookDetail() {
    loadBookDetail(loadBookDetailUseCase)
  }


  private fun loadBookDetail(loadBookDetailUseCase: LoadBookDetailUseCase) {
    disposables.add(loadBookDetailUseCase.execute()
      .observeOn(schedulersFacade.ui())
      .subscribeOn(schedulersFacade.io())
      .doOnSubscribe { disposable -> response.setValue(ResponseLoading()) }
      .delay(3L, TimeUnit.SECONDS, schedulersFacade.ui())
      .subscribe(
        { greeting -> response.setValue(ResponseSuccess(greeting)) },
        { throwable -> response.setValue(ResponseError(throwable)) }
      )
    )
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }

}
