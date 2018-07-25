package com.tomaschlapek.booklibrary.ui.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tomaschlapek.booklibrary.databinding.ItemBookBinding
import com.tomaschlapek.booklibrary.databinding.ItemBookFooterBinding
import com.tomaschlapek.booklibrary.model.BookItem
import com.tomaschlapek.booklibrary.model.BookLoadingFooter
import com.tomaschlapek.booklibrary.model.BookPack
import com.tomaschlapek.booklibrary.ui.adapter.viewholder.BookFooterViewHolder
import com.tomaschlapek.booklibrary.ui.adapter.viewholder.BookViewHolder


const val ITEM_BOOK = 0
const val ITEM_FOOTER_LOADING = 1

class BooksAdapter(context: Context, itemList: MutableList<BookPack>, private val contentClickListener: (BookItem) -> Unit) : BaseAdapter<BookPack>(context, itemList) {

  var isLoading: Boolean = false
  var hasMoreData: Boolean = true

  override fun createCustomViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == ITEM_BOOK) {
      val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      BookViewHolder(binding)
    } else {
      val binding = ItemBookFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      BookFooterViewHolder(binding)
    }
  }

  override fun onBindData(holder: RecyclerView.ViewHolder, value: BookPack) {
    when (holder) {
      is BookViewHolder -> setupBook(holder, value as BookItem)
      is BookFooterViewHolder -> setupProgressFooter(holder)
    }
  }

  override fun onItemViewType(pos: Int) = when (mItemList[pos]) {
    is BookItem -> ITEM_BOOK
    is BookLoadingFooter -> ITEM_FOOTER_LOADING
  }

  override fun countItems() = mItemList.size


  private fun setupBook(holder: BookViewHolder, card: BookItem) {
    holder.bind(card) { card -> contentClickListener.invoke(card) }
  }


  private fun setupProgressFooter(holder: BookFooterViewHolder) {
    holder.bind()
  }

  override fun addItems(savedCardItems: MutableList<BookPack>) {
    val handler = Handler()
    val r = Runnable {
      if (isLoading) {
        isLoading = false
        val lastItemPostion = mItemList.size - 1
        val loadingFooterItem = mItemList[lastItemPostion]
        if (loadingFooterItem is BookLoadingFooter) {
          mItemList.remove(loadingFooterItem)
        }
      }
      mItemList.addAll(savedCardItems)
      notifyDataSetChanged()
    }
    handler.post(r)
  }

  fun addLoadingFooter() {
    if (!isLoading) {
      isLoading = true
      val handler = Handler()
      val r = Runnable {
        mItemList.add(BookLoadingFooter())
        notifyItemInserted(mItemList.size - 1)
      }
      handler.post(r)
    }
  }

  fun removeLoadingFooter() {
    val handler = Handler()
    val r = Runnable {
      if (isLoading) {
        isLoading = false
        val lastItemPostion = mItemList.size - 1
        val loadingFooterItem = mItemList[lastItemPostion]
        if (loadingFooterItem is BookLoadingFooter) {
          mItemList.remove(loadingFooterItem)
        }
        notifyItemRemoved(lastItemPostion)
        hasMoreData = false
      }
    }
    handler.post(r)
  }
}