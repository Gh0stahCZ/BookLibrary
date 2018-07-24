package com.tomaschlapek.booklibrary.di.components

import android.content.Context
import com.tomaschlapek.booklibrary.App
import com.tomaschlapek.booklibrary.di.module.AppModule
import com.tomaschlapek.booklibrary.di.module.BuildersModule
import com.tomaschlapek.booklibrary.di.module.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, NetModule::class, BuildersModule::class))
interface AppComponent : AndroidInjector<App> {

  fun provideContext(): Context

  fun provideRetrofit(): Retrofit

  /**
   * Description:
   * https://proandroiddev.com/dagger-2-component-builder-1f2b91237856
   */
  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: App): Builder

    fun build(): AppComponent
  }

  // TODO Change to androidx fragment when library will be ready
  //  fun inject(fragment: Fragment)



}