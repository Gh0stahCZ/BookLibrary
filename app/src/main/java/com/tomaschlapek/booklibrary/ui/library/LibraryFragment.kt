package com.tomaschlapek.booklibrary.ui.library

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomaschlapek.booklibrary.R

class LibraryFragment : Fragment() {

  companion object {
    fun newInstance() = LibraryFragment()
  }

  private lateinit var viewModel: LibraryViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.library_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(LibraryViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
