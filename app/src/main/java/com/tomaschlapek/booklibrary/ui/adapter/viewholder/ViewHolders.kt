package com.tomaschlapek.booklibrary.ui.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolders<T : ViewDataBinding>(protected var mBinding: T) : RecyclerView.ViewHolder(mBinding.root)