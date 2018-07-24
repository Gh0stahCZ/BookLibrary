package com.tomaschlapek.booklibrary.di.module

import com.tomaschlapek.booklibrary.ui.LibraryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

  @ContributesAndroidInjector
  abstract fun contributeDashActivityInjector(): LibraryActivity

  // TODO Uncomment when library will be ready for androidx fragment
  //    @ContributesAndroidInjector
  //    abstract fun contributeAnotherFragmentActivityInjector(): AnotherFragment

}