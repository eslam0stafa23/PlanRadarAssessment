package com.assessment.planradar.features.home.data.remote

import com.assessment.planradar.BuildConfig
import com.assessment.planradar.features.home.data.models.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
  @GET("data/2.5/weather")
  suspend fun getCityWeather(
    @Query("q") cityName: String? = null,
    @Query("appid") appKey: String = BuildConfig.WEATHER_API_KEY
  ): WeatherData
}