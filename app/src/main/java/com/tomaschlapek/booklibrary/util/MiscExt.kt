package com.tomaschlapek.booklibrary.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.net.ConnectivityManagerCompat.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tomaschlapek.booklibrary.R
import com.tomaschlapek.booklibrary.model.Data
import io.reactivex.Single
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

const val TAG = "MiscExtensions"

fun BottomAppBar.toggleAlignment(fab: FloatingActionButton? = null) {
  val current = fabAlignmentMode
  fabAlignmentMode = current.xor(1)

  fab?.let {
    fab.setImageResource(if (fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) R.drawable.ic_apps_white_24dp else R.drawable.ic_reply_white_24dp)
  }
}

fun RequestBody.bodyToString(): String {
  return try {
    val buffer = Buffer()
    writeTo(buffer)
    buffer.readUtf8()
  } catch (e: IOException) {
    "Do not work :/"
  }
}

val Float.dp: Float
  get() = (this / Resources.getSystem().displayMetrics.density)

val Int.dp: Int
  get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.resString: String
  get() = (Resources.getSystem().getString(this))

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadUrl(url: String?, cropCorners: Boolean = false, placeHolderRes: Int? = null) {

  val requestMaker =
    Picasso.get()
      .load(url)
      .config(Bitmap.Config.RGB_565)
      .error(createRoundedErrorDrawable())

  if (cropCorners) {
    requestMaker.transform(RoundedCornersTransformation(16.dp, 0.dp, RoundedCornersTransformation.CornerType.ALL))
  }

  placeHolderRes?.let {
    requestMaker.placeholder(it)
  }


  requestMaker.into(this, object : Callback {
    override fun onSuccess() {
      Timber.i("Success")
    }

    override fun onError(e: Exception?) {
      Timber.e("Fail : ${e?.localizedMessage}")
    }
  })
}

private fun ImageView.createRoundedErrorDrawable(): RoundedBitmapDrawable {
  val src = BitmapFactory.decodeResource(this.context.resources, R.drawable.no_cover_placeholder);
  val dr = RoundedBitmapDrawableFactory.create(this.context.resources, src)
  dr.cornerRadius = 16f.dp
  return dr
}

fun View.visible() {
  this.visibility = View.VISIBLE
}

fun View.invisible() {
  this.visibility = View.INVISIBLE
}

fun View.gone() {
  this.visibility = View.GONE
}

//Antonio's Leiva way
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
  return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(viewModelFactory: ViewModelProvider.Factory, body: T.() -> Unit): T {
  val vm = getViewModel<T>(viewModelFactory)
  vm.body()
  return vm
}

inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
  return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}


inline fun <reified T : ViewModel> Fragment.withViewModel(viewModelFactory: ViewModelProvider.Factory, body: T.() -> Unit): T {
  val vm = getViewModel<T>(viewModelFactory)
  vm.body()
  return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
  liveData.observe(this, Observer(body))
}

fun <T> createErrorResponse(throwable: Throwable): Single<Response<T>> {
  Timber.e("[%s]::[createErrorResponse]::[%s]", TAG, throwable.message)
  val response: Response<T> = getError(throwable)
  return Single.just(response)
}

fun <T> getError(throwable: Throwable): Response<T> {
  val errorCode = if (throwable is UnknownHostException) {
    ERROR_NO_CONNECTION
  } else {
    HTTP_GENERIC_ERROR_CODE
  }

  val resErr: Response<T> = Response.error(HTTP_GENERIC_ERROR_CODE, ResponseBody
    .create(MediaType.parse("application/json"),
      "{\"error\":{\"field\":\"\",\"message\":\"" + (throwable.localizedMessage
        ?: throwable.message)?.replace("\"", "")?.replace("\'", "") + "\",\"value\":\"\",\"code\":" + errorCode + "}}"))
  return resErr

}

fun checkBackgroundDataRestricted(context: Context): Boolean {
  val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    when (connMgr?.restrictBackgroundStatus) {
      RESTRICT_BACKGROUND_STATUS_ENABLED ->
        // Background data usage and push notifications are blocked for this app
        true

      RESTRICT_BACKGROUND_STATUS_WHITELISTED, RESTRICT_BACKGROUND_STATUS_DISABLED ->
        // Data Saver is disabled or the app is whitelisted
        false
      else ->
        false
    }
  } else false
}

operator fun <T> MutableLiveData<Data<ArrayList<T>>>.plusAssign(values: List<T>?) {
  val value = this.value?.data ?: arrayListOf()
  values?.let {
    value.addAll(it)
  }
  this.value?.data = value
}