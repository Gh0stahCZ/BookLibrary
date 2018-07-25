package com.tomaschlapek.booklibrary.ui.bookdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.model.Response
import com.tomaschlapek.booklibrary.model.ResponseError
import com.tomaschlapek.booklibrary.model.ResponseLoading
import com.tomaschlapek.booklibrary.model.ResponseSuccess
import com.tomaschlapek.booklibrary.util.gone
import com.tomaschlapek.booklibrary.util.observe
import com.tomaschlapek.booklibrary.util.visible
import com.tomaschlapek.booklibrary.util.withViewModel
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
    withViewModel<BookDetailViewModel>(factory) {
      observe(response, ::processResponse)
    }

    init()
  }

  private fun init() {
    detail_trigger_btn.setOnClickListener {
      viewModel.loadBookDetail()
    }
  }

  private fun processResponse(response: Response) {
    when (response) {
      is ResponseLoading -> renderLoadingState()
      is ResponseSuccess -> renderDataState(response.data)
      is ResponseError -> renderErrorState(response.error)
    }
  }

  private fun renderLoadingState() {
    detail_progress_bar.visible()

    book_detail_message.gone()
  }

  private fun renderDataState(greeting: String) {
    detail_progress_bar.gone()

    book_detail_message.visible()
    book_detail_message.text = greeting
  }

  private fun renderErrorState(throwable: Throwable) {
    Timber.e(throwable)

    detail_progress_bar.gone()
    book_detail_message.visible()
    Toast.makeText(context, "Error : ${throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
  }

}
