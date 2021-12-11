package com.assessment.planradar.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assessment.planradar.App
import com.assessment.planradar.di.AppComponent
import timber.log.Timber
import kotlin.math.round

val Context.app: App get() = applicationContext as App

/**
 * enable dagger AppComponent for any Context
 */
val Context.appComponent: AppComponent get() = app.appComponent

/**
 * enable dagger AppComponent for any Fragment
 */
val Fragment.appComponent: AppComponent
  get() = requireContext().app.appComponent

val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(this.context)

/**
 * tag for any class
 */
inline val <reified T> T.TAG: String get() = T::class.java.simpleName

/**
 * log types enabled to any object
 */
inline fun <reified T> T.logv(message: String) = Timber.v(TAG, message)
inline fun <reified T> T.loge(message: String) = Timber.e(TAG, message)
inline fun <reified T> T.loge(message: String, throwable: Throwable) = Timber.e(throwable, message)

inline fun <reified T> T.logd(message: String) = Timber.d(TAG, message)
inline fun <reified T> T.logi(message: String) = Timber.i(TAG, message)
inline fun <reified T> T.logw(message: String) = Timber.w(TAG, message)
inline fun <reified T> T.logwtf(message: String) = Timber.wtf(TAG, message)

fun Double.round(decimals: Int): Double {
  var multiplier = 1.0
  repeat(decimals) { multiplier *= 10 }
  return round(this * multiplier) / multiplier
}