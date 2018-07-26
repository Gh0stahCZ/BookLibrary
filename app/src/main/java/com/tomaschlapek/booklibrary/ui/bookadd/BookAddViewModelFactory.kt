package com.tomaschlapek.booklibrary.ui.bookadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.AddBookUseCase
import com.tomaschlapek.booklibrary.rx.SchedulersFacade

class BookAddViewModelFactory(var picasso: Picasso, val sched: SchedulersFacade, val usecase: AddBookUseCase) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(BookAddViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return BookAddViewModel(picasso, sched, usecase) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}