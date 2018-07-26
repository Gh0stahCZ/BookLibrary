package com.tomaschlapek.booklibrary.ui.bookadd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.databinding.BookAddFragmentBinding
import com.tomaschlapek.booklibrary.model.AddBookBody
import com.tomaschlapek.booklibrary.model.BookDetail
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.util.observe
import com.tomaschlapek.booklibrary.util.withViewModel
import timber.log.Timber


class BookAddFragment : Fragment() {

  var factory: BookAddViewModelFactory = Injector.get().provideBookAddViewModelFactory()
  lateinit var binding: BookAddFragmentBinding

  private lateinit var viewModel: BookAddViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    binding = BookAddFragmentBinding.inflate(layoutInflater, container, false)
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
    binding.bookAddConfirm.setOnClickListener {
      viewModel.addBookDetail(collectBookInfo())
    }
  }

  private fun collectBookInfo() =
    AddBookBody(
      binding.bookAddNameInput.text.toString(),
      binding.bookAddAuthorInput.text.toString(),
      binding.bookAddPriceInput.text.toString().toDoubleOrNull() ?: 0.0
    )

  private fun processResponse(response: Data<BookDetail>) {
    when (response.dataState) {
      DataState.LOADING -> renderLoadingState()
      DataState.SUCCESS -> renderDataState(response.data)
      DataState.ERROR -> renderErrorState(response.message)
    }
  }

  private fun renderLoadingState() {
    setLoading(true)
  }

  private fun renderDataState(bookDetail: BookDetail?) {
    setLoading(false)

    binding.bookAddNameInput.setText(bookDetail?.title)
    binding.bookAddAuthorInput.setText(bookDetail?.author)
    binding.bookAddPriceInput.setText(getString(R.string.currency_price, bookDetail?.price))
  }

  private fun renderErrorState(message: String?) {
    Timber.e(message)
    setLoading(false)

    Toast.makeText(context, "Error : $message", Toast.LENGTH_SHORT).show()
  }

  private fun setLoading(isLoading: Boolean) {
    if (isLoading) {
      //      binding.bookAddContainer.
    }
  }


}
