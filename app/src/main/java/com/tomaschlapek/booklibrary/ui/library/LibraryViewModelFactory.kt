package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class LibraryViewModelFactory(var picasso: Picasso) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return LibraryViewModel(picasso) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}