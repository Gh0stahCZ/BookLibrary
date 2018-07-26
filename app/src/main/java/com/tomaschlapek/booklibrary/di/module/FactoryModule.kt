package com.tomaschlapek.booklibrary.di.module

import com.tomaschlapek.booklibrary.domain.interactor.AddBookUseCase
import com.tomaschlapek.booklibrary.domain.interactor.LoadBookDetailUseCase
import com.tomaschlapek.booklibrary.domain.interactor.LoadLibraryUseCase
import com.tomaschlapek.booklibrary.domain.repository.*
import com.tomaschlapek.booklibrary.rx.SchedulersFacade
import com.tomaschlapek.booklibrary.ui.bookadd.BookAddViewModelFactory
import com.tomaschlapek.booklibrary.ui.bookdetail.BookDetailViewModelFactory
import com.tomaschlapek.booklibrary.ui.library.LibraryViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class FactoryModule {

  @Provides
  @Singleton
  internal fun provideLibraryViewModelFactory(schedulers: SchedulersFacade, useCase: LoadLibraryUseCase): LibraryViewModelFactory {
    return LibraryViewModelFactory(schedulers, useCase)
  }

  @Provides
  @Singleton
  internal fun provideBookDetailViewModelFactory( schedulers: SchedulersFacade, useCase: LoadBookDetailUseCase): BookDetailViewModelFactory {
    return BookDetailViewModelFactory( schedulers, useCase)
  }

  @Provides
  @Singleton
  internal fun provideBookAddViewModelFactory( schedulers: SchedulersFacade, useCase: AddBookUseCase): BookAddViewModelFactory {
    return BookAddViewModelFactory(schedulers, useCase)
  }

  @Provides
  @Singleton
  internal fun provideILibraryRepository(retrofit: Retrofit): ILibraryRepository = LibraryRepository(retrofit)

  @Provides
  @Singleton
  internal fun provideIBookDetailRepository(retrofit: Retrofit): IBookDetailRepository = BookDetailRepository(retrofit)

  @Provides
  @Singleton
  internal fun provideIBookAddRepository(retrofit: Retrofit): IBookAddRepository = BookAddRepository(retrofit)
}