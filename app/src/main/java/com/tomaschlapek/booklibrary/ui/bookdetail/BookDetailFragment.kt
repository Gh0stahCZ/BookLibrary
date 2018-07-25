package com.tomaschlapek.booklibrary.ui.bookdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.util.*
import kotlinx.android.synthetic.main.book_detail_fragment.*
import timber.log.Timber


class BookDetailFragment : Fragment() {

  var factory: BookDetailViewModelFactory = Injector.get().provideBookDetailViewModelFactory()

  private lateinit var viewModel: BookDetailViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.book_detail_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = withViewModel(factory) {
      observe(response, ::processResponse)
    }

    init()
  }

  private fun init() {
    //    detail_trigger_btn.setOnClickListener {
    //    viewModel.loadBookDetail()
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
    detail_progress_bar.visible()

  }

  private fun renderDataState(bookDetail: BookDetail?) {
    detail_progress_bar.gone()

    book_detail_title.text = bookDetail?.title
    book_detail_author.text = bookDetail?.author
    book_detail_price.text = getString(R.string.currency_price, bookDetail?.price)
    imageView.loadUrl(bookDetail?.image, true)
  }

  private fun renderErrorState(message: String?) {
    Timber.e(message)
    detail_progress_bar.gone()

    Toast.makeText(context, "Error : $message", Toast.LENGTH_SHORT).show()
  }

}
