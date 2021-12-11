package com.assessment.planradar.features.home.domain

import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.utils.result.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(private val mainRepository: MainRepository) {

  fun get(cityName: String?): Flow<Resource<WeatherData>> =
    mainRepository.getCityWeather(cityName)
}