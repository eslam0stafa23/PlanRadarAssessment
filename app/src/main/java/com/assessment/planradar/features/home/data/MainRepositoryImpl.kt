package com.assessment.planradar.features.home.data

import android.annotation.SuppressLint
import com.assessment.planradar.features.home.data.local.CitiesDao
import com.assessment.planradar.features.home.data.local.WeatherDao
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.features.home.data.remote.WeatherApiService
import com.assessment.planradar.features.home.domain.MainRepository
import com.assessment.planradar.utils.Const
import com.assessment.planradar.utils.result.FetchLimiter
import com.assessment.planradar.utils.result.NetworkBoundResource
import com.assessment.planradar.utils.result.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
  private val citiesDao: CitiesDao,
  private val weatherDao: WeatherDao,
  private val weatherApiService: WeatherApiService,
) : MainRepository {
  var fetchLimiter: FetchLimiter<String> = FetchLimiter(1, TimeUnit.MINUTES)

  /**
   * this method is used to delete a specified city from the database
   * @param city City
   */
  override suspend fun removeCity(city: City) {
    citiesDao.delete(city)
  }

  /**
   * this method is used for returning a list of the saved cities on the database
   * @return Flow<List<City>> cities list
   */
  override fun getAllCities(): Flow<List<City>> {
    return citiesDao.getAllCities()
  }

  /**
   * this method is used to check if the passed city name is a valid city name or not by calling
   * the weather API and if the response succeeded then the city will be added to the database
   * and if not return error to the method caller
   * @param cityName String
   * @return Flow<Resource<Any>> return success or error
   */
  override fun checkAddCity(cityName: String): Flow<Resource<Any>> {
    return flow {
      emit(Resource.loading())
      try {
        val apiResponse = weatherApiService.getCityWeather(cityName)
        val city = City(apiResponse.cityId, apiResponse.cityName, apiResponse.sys.countryCode)
        citiesDao.insert(city)
        emit(Resource.success())
      } catch (e: IOException) {
        emit(Resource.error())
      }
    }
  }

  /**
   * this method is used to call the API and get the current weather for a specified city, it saves
   * the weather data on the database and then return the data to the method caller
   *
   * the method takes the city name as a parameter and returns weather data
   * @param cityName String?
   * @return Flow<Resource<WeatherData>>
   */
  @SuppressLint("SimpleDateFormat")
  override fun getCityWeather(cityName: String?): Flow<Resource<WeatherData>> {
    return flow {
      emit(Resource.loading())
      try {
        val apiResponse = weatherApiService.getCityWeather(cityName)
        val dateFormat = SimpleDateFormat(Const.DATE_TIME_FORMAT)
        val currentDate = dateFormat.format(Date())
        apiResponse.dateTime = currentDate
        weatherDao.insert(apiResponse)
        emit(Resource.success(apiResponse))
      } catch (e: IOException) {
        emit(Resource.error())
      }
    }
  }

  /**
   * this method is used for returning a list of saved weather data for a specified city.
   *
   * firstly it calls the API and get the current weather, then save it to the database and
   * finally returns the list of all weather data for the specified city.
   *
   * @param cityName String
   * @return Flow<Resource<List<WeatherData>>>
   */
  override fun getWeatherHistory(cityName: String): Flow<Resource<List<WeatherData>>> {
    return object : NetworkBoundResource<List<WeatherData>, WeatherData>() {
      override suspend fun fetchFromNetwork(): WeatherData {
        return weatherApiService.getCityWeather(cityName)
      }

      override fun loadFromDb(): Flow<List<WeatherData>> {
        return weatherDao.getWeatherHistory(cityName)
      }

      @SuppressLint("SimpleDateFormat")
      override suspend fun saveNetworkResult(response: WeatherData) {
        val dateFormat = SimpleDateFormat(Const.DATE_TIME_FORMAT)
        val currentDate = dateFormat.format(Date())
        response.dateTime = currentDate
        return weatherDao.insert(response)
      }

      override fun shouldFetch(data: List<WeatherData>): Boolean {
        return data.isEmpty() || fetchLimiter.shouldFetch(cityName)
      }

      override fun onFetchFailed() {
        fetchLimiter.reset(cityName)
      }
    }.asFlow()
  }
}

