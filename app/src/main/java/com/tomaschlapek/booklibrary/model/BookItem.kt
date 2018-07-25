package com.tomaschlapek.booklibrary.model

sealed class BookPack

data class BookItem(val id: Int, val link: String, val title: String) : BookPack()

class BookLoadingFooter : BookPack()

