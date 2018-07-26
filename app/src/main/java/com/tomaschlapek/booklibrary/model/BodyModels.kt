package com.tomaschlapek.booklibrary.model

data class AddBookBody(val title: String, val author: String, val price: Double, val image: String? = null)
