package com.tomaschlapek.booklibrary.ui.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaschlapek.booklibrary.domain.interactor.LoadBookDetailUseCase
import com.tomaschlapek.booklibrary.rx.SchedulersFacade

class BookDetailViewModelFactory(val schedulersFacade: SchedulersFacade, val loadBookDetailUseCase: LoadBookDetailUseCase) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return BookDetailViewModel(schedulersFacade, loadBookDetailUseCase) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}