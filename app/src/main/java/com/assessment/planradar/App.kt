package com.assessment.planradar

import android.app.Application
import com.assessment.planradar.di.AppComponent
import com.assessment.planradar.di.ContextModule
import com.assessment.planradar.di.DaggerAppComponent

class App : Application() {
  lateinit var appComponent: AppComponent
    private set

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.factory()
      .create(ContextModule(this))
  }
}