package com.tomaschlapek.booklibrary.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    checkAndLoad()
  }

  public fun checkAndLoad() {
    val visibleItemCount = layoutManager.childCount
    val totalItemCount = layoutManager.itemCount
    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

    if (!isLoading() && !isLastPage()) {
      if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
        loadMoreItems()
      }
    }
  }

  protected abstract fun loadMoreItems()

  //    abstract fun getTotalPageCoutn(): Int

  abstract fun isLastPage(): Boolean

  abstract fun isLoading(): Boolean

}