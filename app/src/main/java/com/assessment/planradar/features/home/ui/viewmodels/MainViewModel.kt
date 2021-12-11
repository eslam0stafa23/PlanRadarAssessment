package com.assessment.planradar.features.home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.features.home.domain.CheckAddCityUseCase
import com.assessment.planradar.features.home.domain.GetAllCitiesUseCase
import com.assessment.planradar.features.home.domain.GetCityWeatherUseCase
import com.assessment.planradar.features.home.domain.GetWeatherHistoryUseCase
import com.assessment.planradar.features.home.domain.RemoveCityUseCase
import com.assessment.planradar.utils.asMappedResourceLiveData
import com.assessment.planradar.utils.asResourceLiveData
import com.assessment.planradar.utils.result.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val getAllCitiesUseCase: GetAllCitiesUseCase,
  private val getCityWeatherUseCase: GetCityWeatherUseCase,
  private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase,
  private val checkAddCityUseCase: CheckAddCityUseCase,
  private val removeCityUseCase: RemoveCityUseCase,
) : ViewModel() {

  fun checkAddCity(cityName: String): LiveData<Resource<Any>> =
    checkAddCityUseCase.execute(cityName).asMappedResourceLiveData("checkAddCity")

  fun removeCity(city: City) {
    viewModelScope.launch(Dispatchers.IO) {
      removeCityUseCase.execute(city)
    }
  }

  fun getAllCities(): LiveData<Resource<List<City>>> =
    getAllCitiesUseCase.get().asResourceLiveData("getAllCities")

  fun getCityWeather(cityName: String?): LiveData<Resource<WeatherData>> =
    getCityWeatherUseCase.get(cityName).asMappedResourceLiveData("getCityWeather")

  fun getCityWeatherHistory(cityName: String): LiveData<Resource<List<WeatherData>>> =
    getWeatherHistoryUseCase.get(cityName).asMappedResourceLiveData("getCityWeatherHistory")

}