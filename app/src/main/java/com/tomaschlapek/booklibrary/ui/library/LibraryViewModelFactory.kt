package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.LoadLibraryUseCase
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import javax.inject.Inject

class LibraryViewModelFactory @Inject constructor(var picasso: Picasso, val schedulersFacade: SchedulersFacade, val loadBookDetailUseCase: LoadLibraryUseCase) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return LibraryViewModel(picasso, schedulersFacade, loadBookDetailUseCase) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}