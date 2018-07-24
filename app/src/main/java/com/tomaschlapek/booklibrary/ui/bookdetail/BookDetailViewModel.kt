package com.tomaschlapek.booklibrary.ui.bookdetail

import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import timber.log.Timber

class BookDetailViewModel(var picasso: Picasso) : ViewModel() {

  init {
    Timber.i("Hello from Book Detail ViewModel")
  }

}
