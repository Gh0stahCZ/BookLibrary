package com.tomaschlapek.booklibrary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.bottomappbar.BottomAppBar
import com.tomaschlapek.booklibrary.R
import kotlinx.android.synthetic.main.library_activity.*

class LibraryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.library_activity)

    init()
  }

  private fun init() {
    Navigation.findNavController(this, R.id.container).addOnNavigatedListener { controller, destination ->
      run {
        when (destination.id) {
          R.id.libraryFragment -> {
            bottom_appbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            fab.setImageResource(R.drawable.ic_add_circle_white24dp)
            fab.setOnClickListener {
              Navigation.findNavController(this, R.id.container).navigate(R.id.action_libraryFragment_to_bookAddFragment)
            }
          }
          R.id.bookAddFragment,
          R.id.bookDetailFragment -> {
            bottom_appbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            fab.setImageResource(R.drawable.ic_reply_white_24dp)
            fab.setOnClickListener {
              Navigation.findNavController(this, R.id.container).popBackStack()
            }
          }
          else -> {
            fab.setOnClickListener(null)
          }
        }

      }
    }
  }

}
