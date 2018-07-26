package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomaschlapek.booklibrary.domain.interactor.ILoadLibraryUseCase
import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import com.tomaschlapek.booklibrary.util.LIBRARY_LIMIT
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class LibraryViewModel(val sched: SchedulersFacade, val usecase: ILoadLibraryUseCase) : ViewModel() {

  private val disposables = CompositeDisposable()
  val response = MutableLiveData<Data<List<BookItem>>>()
  private var currentPage = 0

  init {
    Timber.i("Hello from Library ViewModel")
    loadLibrary()
  }

  fun loadLibraryNextPage() {
    currentPage += 1
    loadLibrary(usecase, currentPage)
  }

  fun loadLibrary() {
    currentPage = 0
    loadLibrary(usecase)
  }

  private fun loadLibrary(useCase: ILoadLibraryUseCase, page: Int = 0, limit: Int = LIBRARY_LIMIT) {
    disposables.add(useCase.execute(page * limit, limit)
      .observeOn(sched.ui())
      .subscribeOn(sched.io())
      .doOnSubscribe { response.setValue(Data(dataState = DataState.LOADING, data = response.value?.data, message = response.value?.message)) }
      //      .delay(3L, TimeUnit.SECONDS, sched.ui())
      .subscribe(
        { response ->
          if (response.isSuccessful) {
            this.response.setValue(Data(dataState = DataState.SUCCESS, data = response.body(), message = page.toString()))
          } else {
            this.response.setValue(Data(dataState = DataState.ERROR, data = null, message = response.errorBody()?.string()))
          }
        },
        { throwable -> response.setValue(Data(dataState = DataState.SUCCESS, data = response.value?.data, message = throwable.localizedMessage)) }
      )
    )
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }

}
