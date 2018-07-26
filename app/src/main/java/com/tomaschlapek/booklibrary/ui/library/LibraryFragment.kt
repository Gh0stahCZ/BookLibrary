package com.tomaschlapek.booklibrary.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.databinding.LibraryFragmentBinding
import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.model.BookPack
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.ui.adapter.BooksAdapter
import com.tomaschlapek.booklibrary.util.observe
import com.tomaschlapek.booklibrary.util.withViewModel
import com.tomaschlapek.booklibrary.widget.PaginationScrollListener
import timber.log.Timber

class LibraryFragment : Fragment() {

  var factory: LibraryViewModelFactory = Injector.get().provideLibraryViewModelFactory()
  lateinit var adapter: BooksAdapter
  lateinit var scrollListener: PaginationScrollListener
  lateinit var binding: LibraryFragmentBinding

  private var isLoading = false
  private var isLastPage = false

  private lateinit var viewModel: LibraryViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    binding = LibraryFragmentBinding.inflate(layoutInflater, container, false)
    init()
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = withViewModel(factory) {
      observe(response, ::processResponse)
    }
  }


  override fun onDestroy() {
    super.onDestroy()
    viewModel.response.removeObservers(this)
  }

  private fun processResponse(response: Data<List<BookItem>>) {
    when (response.dataState) {
      DataState.LOADING -> renderLoadingState()
      DataState.SUCCESS -> renderDataState(response.data)
      DataState.ERROR -> renderErrorState(response.message)
    }
  }

  private fun renderLoadingState() {
    binding.librarySwipeProgress.isRefreshing = true
  }

  private fun renderDataState(bookDetail: List<BookItem>?) {
    binding.librarySwipeProgress.isRefreshing = false

    //    detail_button.text = bookDetail?.firstOrNull()?.title
    val outputList = mutableListOf<BookPack>()
    bookDetail?.forEach {
      outputList.add(it)
    }

    adapter.addItems(outputList)

    //    book_detail_author.text = bookDetail?.author
    //    book_detail_price.text = getString(R.string.currency_price, bookDetail?.price)
    //    imageView.loadUrl(bookDetail?.image, true)
  }

  private fun renderErrorState(message: String?) {
    Timber.e(message)
    binding.librarySwipeProgress.isRefreshing = false

    Toast.makeText(context, "Error : $message", Toast.LENGTH_SHORT).show()
  }

  private fun init() {
    binding.detailButton.setOnClickListener {
      openBookDetail()
    }

    context?.let {
      adapter = BooksAdapter(it, mutableListOf()) { openBookDetail() }

      initScrollListener()

      binding.libraryRecyclerView.adapter = adapter
      binding.libraryRecyclerView.addOnScrollListener(scrollListener)

      binding.librarySwipeProgress.setOnRefreshListener {
        viewModel.loadLibrary()
      }

    }
  }

  private fun loadNextPage() {
    adapter.addLoadingFooter()
    viewModel.loadLibrary()
  }

  private fun openBookDetail() {
    activity?.let {
      Navigation.findNavController(it, R.id.container).navigate(R.id.action_libraryFragment_to_bookDetailFragment2)
    }
  }

  private fun initScrollListener() {
    scrollListener = OnScrollListener(binding.libraryRecyclerView.layoutManager as LinearLayoutManager)

//    scrollListener = object : PaginationScrollListener(binding.libraryRecyclerView.layoutManager as LinearLayoutManager) {
//      override fun loadMoreItems() {
//        viewModel.loadNextPage()
//        adapter.addLoadingFooter()
//      }
//
//      override fun isLastPage(): Boolean {
//        return !adapter.hasMoreData
//      }
//
//      override fun isLoading(): Boolean {
//        return adapter.isLoading
//      }
//    }
  }

  inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
    override fun isLoading() = isLoading
    override fun loadMoreItems() = loadNextPage()
    override fun isLastPage() = isLastPage
  }

}
