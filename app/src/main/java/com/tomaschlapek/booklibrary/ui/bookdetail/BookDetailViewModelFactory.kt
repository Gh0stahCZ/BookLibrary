package com.tomaschlapek.booklibrary.ui.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class BookDetailViewModelFactory(var picasso: Picasso) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return BookDetailViewModel(picasso) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}