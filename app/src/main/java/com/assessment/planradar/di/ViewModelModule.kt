package com.assessment.planradar.di

import androidx.lifecycle.ViewModel
import com.assessment.planradar.di.annotitions.ViewModelKey
import com.assessment.planradar.features.home.ui.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
  @Binds @IntoMap @ViewModelKey(MainViewModel::class)
  abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}