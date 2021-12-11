package com.assessment.planradar.features.home.data.local

import androidx.room.Dao
import androidx.room.Query
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao : BaseDao<WeatherData> {
  @Query("SELECT * FROM weatherdata WHERE cityName IN(:cityName)")
  abstract fun getWeatherHistory(cityName: String): Flow<List<WeatherData>>
}