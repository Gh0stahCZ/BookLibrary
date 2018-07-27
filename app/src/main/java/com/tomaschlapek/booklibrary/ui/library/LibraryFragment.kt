package com.tomaschlapek.booklibrary.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomaschlapek.booklibrary.Injector
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.model.BookPack
import com.tomaschlapek.booklibrary.model.Data
import com.tomaschlapek.booklibrary.model.DataState
import com.tomaschlapek.booklibrary.ui.adapter.BooksAdapter
import com.tomaschlapek.booklibrary.util.observe
import com.tomaschlapek.booklibrary.util.withViewModel
import com.tomaschlapek.booklibrary.widget.PaginationScrollListener
import kotlinx.android.synthetic.main.library_fragment.*
import timber.log.Timber

class LibraryFragment : Fragment() {

  var factory: LibraryViewModelFactory = Injector.get().provideLibraryViewModelFactory()
  lateinit var adapter: BooksAdapter
  lateinit var scrollListener: PaginationScrollListener

  // TODO Add data binding when Android bug with Navigation Component will be solved
  //  lateinit var binding: LibraryFragmentBinding

  private var isLoading = false
  private var isLastPage = false

  private lateinit var viewModel: LibraryViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    //    binding = LibraryFragmentBinding.inflate(layoutInflater, container, false)
    //    return binding.root
    return inflater.inflate(R.layout.library_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = withViewModel(factory) {
      observe(liveData, ::processResponse)
    }

    // because of presentation of pagination with limit 3 item per page, new request is called
    // otherwise would be only received last data from LiveData object
//    savedInstanceState?.let {
//      viewModel.loadLibrary()
//    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    viewModel.liveData.removeObservers(this)
  }


  private fun processResponse(response: Data<ArrayList<BookItem>>) {
    when (response.dataState) {
      DataState.LOADING -> renderLoadingState()
      DataState.SUCCESS -> renderDataState(response.data, response.message)
      DataState.ERROR -> renderErrorState(response.message)
    }
  }

  private fun renderLoadingState() {
    library_swipe_progress.isRefreshing = true
  }

  private fun renderDataState(bookDetail: List<BookItem>?, page: String?) {
    library_swipe_progress.isRefreshing = false

    val outputList = mutableListOf<BookPack>()
    bookDetail?.forEach {
      outputList.add(it)
    }

    if (outputList.isEmpty()) { // stop loading (no more items to load)
      adapter.removeLoadingFooter()
    } else {
      if (page?.equals("0") == true) {
        adapter.setItems(outputList) // clear previous list and set new items
      } else {
        adapter.setItems(outputList) // add items to current state
      }
    }
//    scrollListener.checkAndLoad()
  }

  private fun renderErrorState(message: String?) {
    Timber.e(message)
    library_swipe_progress.isRefreshing = false

    Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
  }

  private fun init() {
    context?.let {
      adapter = BooksAdapter(it, mutableListOf()) { openBookDetail(it) }

      initScrollListener()

      library_recycler_view.adapter = adapter
      library_recycler_view.addOnScrollListener(scrollListener)

      library_swipe_progress.setOnRefreshListener {
        refreshItems()
      }

    }
  }

  private fun refreshItems() {
    adapter.hasMoreData = true
    viewModel.loadLibrary()
  }

  private fun loadNextPage() {
    adapter.addLoadingFooter()
    viewModel.loadLibrary()
  }

  private fun openBookDetail(bookItem: BookItem) {
    activity?.let {
      var bundle = bundleOf(getString(R.string.arg_book_id) to bookItem.id)
      Navigation.findNavController(it, R.id.container).navigate(R.id.action_libraryFragment_to_bookDetailFragment2, bundle)
    }
  }

  private fun initScrollListener() {
    scrollListener = OnScrollListener(library_recycler_view.layoutManager as LinearLayoutManager)

    scrollListener = object : PaginationScrollListener(library_recycler_view.layoutManager as LinearLayoutManager) {
      override fun loadMoreItems() {
        viewModel.loadLibraryNextPage()
        adapter.addLoadingFooter()
      }

      override fun isLastPage(): Boolean {
        return !adapter.hasMoreData
      }

      override fun isLoading(): Boolean {
        return adapter.isLoading
      }
    }
  }

  inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
    override fun isLoading() = isLoading
    override fun loadMoreItems() = loadNextPage()
    override fun isLastPage() = isLastPage
  }

}
