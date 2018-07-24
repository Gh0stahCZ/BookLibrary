package com.tomaschlapek.booklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tomaschlapek.booklibrary.ui.library.LibraryFragment

class LibraryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.library_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, LibraryFragment.newInstance())
        .commitNow()
    }
  }

}
