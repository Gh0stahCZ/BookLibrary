package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tomaschlapek.booklibrary.domain.interactor.ILoadLibraryUseCase
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import javax.inject.Inject

class LibraryViewModelFactory @Inject constructor(val schedulersFacade: SchedulersFacade, val loadBookDetailUseCase: ILoadLibraryUseCase) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return LibraryViewModel(schedulersFacade, loadBookDetailUseCase) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}