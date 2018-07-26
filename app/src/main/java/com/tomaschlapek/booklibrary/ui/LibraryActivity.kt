package com.tomaschlapek.booklibrary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.bottomappbar.BottomAppBar
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.databinding.LibraryActivityBinding


class LibraryActivity : AppCompatActivity() {

  lateinit var binding: LibraryActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //    setContentView(R.layout.library_activity)
    binding = DataBindingUtil.setContentView(this, R.layout.library_activity)
    init()
  }

  private fun init() {
    Navigation.findNavController(this, R.id.container).addOnNavigatedListener { _, destination ->
      run {
        when (destination.id) {
          R.id.libraryFragment -> {
            binding.bottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            binding.fab.setImageResource(R.drawable.ic_add_circle_white24dp)
            binding.fab.setOnClickListener {
              Navigation.findNavController(this, R.id.container).navigate(R.id.action_libraryFragment_to_bookAddFragment)
            }
          }
          R.id.bookAddFragment,
          R.id.bookDetailFragment -> {
            binding.bottomAppbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            binding.fab.setImageResource(R.drawable.ic_reply_white_24dp)
            binding.fab.setOnClickListener {
              Navigation.findNavController(this, R.id.container).popBackStack()
            }
          }
          else -> {
            binding.fab.setOnClickListener(null)
          }
        }
      }
    }
  }
}
