package com.assessment.planradar.di

import android.content.Context
import androidx.room.Room
import com.assessment.planradar.utils.Const
import com.assessment.planradar.utils.db.MainRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
  @Singleton @Provides
  fun providesRoomDatabase(context: Context): MainRoomDatabase {
    return Room.databaseBuilder(
      context.applicationContext, MainRoomDatabase::class.java, Const.KEY_DATABASE_NAME
    ).build()
  }

  @Singleton @Provides
  fun provideCitiesDao(db: MainRoomDatabase) = db.citiesDao()

  @Singleton @Provides
  fun provideWeathersDao(db: MainRoomDatabase) = db.weathersDao()
}