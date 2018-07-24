package com.tomaschlapek.booklibrary.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import kotlinx.android.synthetic.main.library_fragment.*

class LibraryFragment : Fragment() {

  companion object {
    fun newInstance() = LibraryFragment()
  }

  var factory: LibraryViewModelFactory = Injector.get().provideLibraryViewModelFactory()

  private lateinit var viewModel: LibraryViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.library_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, factory).get(LibraryViewModel::class.java)

    init()
  }

  private fun init() {

    detail_button.setOnClickListener {
      activity?.let {
        Navigation.findNavController(it, R.id.container).navigate(R.id.action_libraryFragment_to_bookDetailFragment2)
      }
    }

  }

}
