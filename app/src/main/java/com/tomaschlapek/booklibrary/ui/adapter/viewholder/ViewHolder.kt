package com.tomaschlapek.booklibrary.ui.adapter.viewholder

import com.tomaschlapek.booklibrary.databinding.ItemBookBinding
import com.tomaschlapek.booklibrary.databinding.ItemBookFooterBinding
import com.tomaschlapek.booklibrary.model.BookItem

class BookViewHolder(binding: ItemBookBinding) : ViewHolders<ItemBookBinding>(binding) {
  fun bind(card: BookItem, contentClickListener: (BookItem) -> Unit) = with(mBinding) {
    bookItemText.text = card.title
    bookItemText.setOnClickListener { _ -> contentClickListener(card) }

  }
}

class BookFooterViewHolder(binding: ItemBookFooterBinding) : ViewHolders<ItemBookFooterBinding>(binding) {
  fun bind() = with(mBinding) {
    mBinding
  }
}