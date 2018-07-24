package com.tomaschlapek.booklibrary.di.module

import android.content.Context
import com.tomaschlapek.booklibrary.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

  @Provides
  @Singleton
  internal fun provideContext(application: App): Context {
    return application.applicationContext
  }

}
