package com.assessment.planradar.di

import com.assessment.planradar.features.home.data.MainRepositoryImpl
import com.assessment.planradar.features.home.domain.MainRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoriesModule {
  @Singleton @Binds
  abstract fun bindMainRepository(mainRepository: MainRepositoryImpl?): MainRepository?
}