package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import timber.log.Timber

class LibraryViewModel(var picasso: Picasso) : ViewModel() {

  init {
    Timber.i("Hello from ViewModel")
  }

}
