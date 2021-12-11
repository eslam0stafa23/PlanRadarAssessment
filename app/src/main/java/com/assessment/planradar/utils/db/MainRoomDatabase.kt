package com.assessment.planradar.utils.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.assessment.planradar.features.home.data.converters.MainConverter
import com.assessment.planradar.features.home.data.converters.SysConverter
import com.assessment.planradar.features.home.data.converters.WeatherListConverter
import com.assessment.planradar.features.home.data.converters.WindConverter
import com.assessment.planradar.features.home.data.local.CitiesDao
import com.assessment.planradar.features.home.data.local.WeatherDao
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.data.models.WeatherData

@Database(entities = [City::class, WeatherData::class], version = 1, exportSchema = false)
@TypeConverters(
  WeatherListConverter::class, MainConverter::class, SysConverter::class,
  WindConverter::class
)
abstract class MainRoomDatabase : RoomDatabase() {
  abstract fun citiesDao(): CitiesDao
  abstract fun weathersDao(): WeatherDao
}

