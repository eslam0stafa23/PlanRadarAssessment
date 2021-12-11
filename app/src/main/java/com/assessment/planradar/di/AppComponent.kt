package com.assessment.planradar.di

import android.content.Context
import com.assessment.planradar.features.home.ui.activities.MainActivity
import com.assessment.planradar.features.home.ui.fragments.AddCityBottomSheet
import com.assessment.planradar.features.home.ui.fragments.HomeFragment
import com.assessment.planradar.features.home.ui.fragments.WeatherDetailsFragment
import com.assessment.planradar.features.home.ui.fragments.WeatherHistoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ContextModule::class, ViewModelModule::class, RepositoriesModule::class,
    RoomModule::class, WebServiceModule::class
  ]
)
interface AppComponent {
  val context: Context

  fun inject(mainActivity: MainActivity)

  // fragments
  fun inject(homeFragment: HomeFragment)
  fun inject(weatherDetailsFragment: WeatherDetailsFragment)
  fun inject(weatherHistoryFragment: WeatherHistoryFragment)
  fun inject(addCityBottomSheet: AddCityBottomSheet)

  @Component.Factory
  interface Factory {
    fun create(contextModule: ContextModule): AppComponent
  }
}