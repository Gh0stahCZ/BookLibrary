package com.tomaschlapek.booklibrary.di.module

import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.ui.bookdetail.BookDetailViewModelFactory
import com.tomaschlapek.booklibrary.ui.library.LibraryViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FactoryModule {

  @Provides
  @Singleton
  internal fun provideLibraryViewModelFactory(picasso: Picasso): LibraryViewModelFactory {
    return LibraryViewModelFactory(picasso)
  }

  @Provides
  @Singleton
  internal fun provideBookDetailViewModelFactory(picasso: Picasso): BookDetailViewModelFactory {
    return BookDetailViewModelFactory(picasso)
  }
}