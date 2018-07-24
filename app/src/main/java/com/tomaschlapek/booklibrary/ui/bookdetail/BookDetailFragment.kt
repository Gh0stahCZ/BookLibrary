package com.tomaschlapek.booklibrary.ui.bookdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R

class BookDetailFragment : Fragment() {

  companion object {
    fun newInstance() = BookDetailFragment()
  }

  var factory: BookDetailViewModelFactory = Injector.get().provideBookDetailViewModelFactory()

  private lateinit var viewModel: BookDetailViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.book_detail_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this, factory).get(BookDetailViewModel::class.java)
  }

}
