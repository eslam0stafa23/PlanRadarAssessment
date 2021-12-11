package com.assessment.planradar.features.home.domain

import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.utils.result.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

  suspend fun removeCity(city: City)

  fun checkAddCity(cityName: String): Flow<Resource<Any>>

  fun getAllCities(): Flow<List<City>>

  fun getWeatherHistory(cityName: String): Flow<Resource<List<WeatherData>>>

  fun getCityWeather(cityName: String?): Flow<Resource<WeatherData>>
}