package com.tomaschlapek.booklibrary

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tomaschlapek.booklibrary.di.components.AppComponent
import com.tomaschlapek.booklibrary.di.components.DaggerAppComponent
import com.tomaschlapek.booklibrary.widget.DebugTree
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasActivityInjector /*, HasFragmentInjector */ {

  /* Public Constants *****************************************************************************/

  @Inject
  lateinit var mActivityInjector: DispatchingAndroidInjector<Activity>

  // TODO Change to androidx fragment when library will be ready
  //  @Inject
  //  lateinit var mFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

  override fun onCreate() {
    super.onCreate()

    INSTANCE = this

    // Start monitoring crashes.
    if (BuildConfig.DEBUG_MODE) {
      Timber.plant(DebugTree())
    } else {
      // TODO Uncomment after Crashlytics addition
      //  Fabric.with(this, new Crashlytics());
      //  Timber.plant(new KrashlyticsTree());
      Timber.plant(DebugTree())
    }

    sAppComponent = DaggerAppComponent.builder()
      .application(this)
      .build()

    sAppComponent.inject(this)

    registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        handleActivity(activity)
      }

      override fun onActivityStarted(activity: Activity) {
      }

      override fun onActivityResumed(activity: Activity) {
      }

      override fun onActivityPaused(activity: Activity) {
      }

      override fun onActivityStopped(activity: Activity) {
      }

      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
      }

      override fun onActivityDestroyed(activity: Activity) {
      }
    })
  }

  override fun activityInjector(): AndroidInjector<Activity> {
    return mActivityInjector
  }

  private fun handleActivity(activity: Activity) {
//    if (activity is HasSupportFragmentInjector) {
      AndroidInjection.inject(activity)
//    }
    if (activity is FragmentActivity) {
      activity.supportFragmentManager
        .registerFragmentLifecycleCallbacks(
          object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
              super.onFragmentCreated(fm, f, savedInstanceState)
              // TODO change when Dagger add AndroidX fragment support
              if (f is android.app.Fragment) {
                AndroidInjection.inject(f as android.app.Fragment)
              }
            }
          }, true)
    }
  }


  // TODO Change to androidx fragment when library will be ready
  //  override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
  //    return mFragmentInjector
  //  }

  lateinit var sAppComponent: AppComponent
    private set

  companion object {
    private var INSTANCE: App? = null

    @JvmStatic
    fun get(): App = INSTANCE!!
  }

}

class Injector private constructor() {
  companion object {
    fun get(): AppComponent = App.get().sAppComponent;
  }
}
