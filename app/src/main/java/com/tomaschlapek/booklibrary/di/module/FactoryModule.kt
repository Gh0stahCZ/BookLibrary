package com.tomaschlapek.booklibrary.di.module

import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.domain.interactor.AddBookUseCase
import com.tomaschlapek.booklibrary.domain.interactor.LoadBookDetailUseCase
import com.tomaschlapek.booklibrary.domain.interactor.LoadLibraryUseCase
import com.tomaschlapek.booklibrary.domain.repository.BookDetailRepository
import com.tomaschlapek.booklibrary.domain.repository.IBookDetailRepository
import com.tomaschlapek.booklibrary.domain.repository.ILibraryRepository
import com.tomaschlapek.booklibrary.domain.repository.LibraryRepository
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import com.tomaschlapek.booklibrary.ui.bookadd.BookAddViewModelFactory
import com.tomaschlapek.booklibrary.ui.bookdetail.BookDetailViewModelFactory
import com.tomaschlapek.booklibrary.ui.library.LibraryViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FactoryModule {

  @Provides
  @Singleton
  internal fun provideLibraryViewModelFactory(picasso: Picasso, schedulers: SchedulersFacade, useCase: LoadLibraryUseCase): LibraryViewModelFactory {
    return LibraryViewModelFactory(picasso, schedulers, useCase)
  }

  @Provides
  @Singleton
  internal fun provideBookDetailViewModelFactory(picasso: Picasso, schedulers: SchedulersFacade, useCase: LoadBookDetailUseCase): BookDetailViewModelFactory {
    return BookDetailViewModelFactory(picasso, schedulers, useCase)
  }

  @Provides
  @Singleton
  internal fun provideBookAddViewModelFactory(picasso: Picasso, schedulers: SchedulersFacade, useCase: AddBookUseCase): BookAddViewModelFactory {
    return BookAddViewModelFactory(picasso, schedulers, useCase)
  }

  @Provides
  @Singleton
  internal fun provideILibraryRepository(): ILibraryRepository = LibraryRepository()

  @Provides
  @Singleton
  internal fun provideIBookDetailRepository(): IBookDetailRepository = BookDetailRepository()
}