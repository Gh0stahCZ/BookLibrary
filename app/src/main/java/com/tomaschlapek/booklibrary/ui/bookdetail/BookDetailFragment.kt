package com.tomaschlapek.booklibrary.ui.bookdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.databinding.BookDetailFragmentBinding
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.util.*
import timber.log.Timber


class BookDetailFragment : Fragment() {

  var factory: BookDetailViewModelFactory = Injector.get().provideBookDetailViewModelFactory()

  lateinit var binding: BookDetailFragmentBinding

  private lateinit var viewModel: BookDetailViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {

    binding = BookDetailFragmentBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = withViewModel(factory) {
      observe(response, ::processResponse)
    }

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
  }


  override fun onDestroy() {
    super.onDestroy()
    viewModel.response.removeObservers(this)
  }

  private fun init() {
    //    detail_trigger_btn.setOnClickListener {
    //    viewModel.loadLibrary()
    //    }
  }

  private fun processResponse(response: Data<BookDetail>) {
    when (response.dataState) {
      DataState.LOADING -> renderLoadingState()
      DataState.SUCCESS -> renderDataState(response.data)
      DataState.ERROR -> renderErrorState(response.message)
    }
  }

  private fun renderLoadingState() {
    binding.detailProgressBar.visible()
  }

  private fun renderDataState(bookDetail: BookDetail?) {
    binding.detailProgressBar.gone()

    binding.bookDetailTitle.text = bookDetail?.title
    binding.bookDetailAuthor.text = bookDetail?.author
    binding.bookDetailPrice.text = getString(R.string.currency_price, bookDetail?.price)
    binding.imageView.loadUrl(bookDetail?.image, true)
  }

  private fun renderErrorState(message: String?) {
    Timber.e(message)
    binding.detailProgressBar.gone()

    Toast.makeText(context, "Error : $message", Toast.LENGTH_SHORT).show()
  }

}
