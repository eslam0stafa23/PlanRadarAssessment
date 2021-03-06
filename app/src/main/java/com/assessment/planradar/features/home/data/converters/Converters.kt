package com.assessment.planradar.features.home.data.converters

import androidx.room.TypeConverter
import com.assessment.planradar.features.home.data.models.WeatherData.Main
import com.assessment.planradar.features.home.data.models.WeatherData.Sys
import com.assessment.planradar.features.home.data.models.WeatherData.Weather
import com.assessment.planradar.features.home.data.models.WeatherData.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherListConverter {
  private val type = object : TypeToken<List<Weather>>() {}.type

  @TypeConverter
  fun toJson(obj: List<Weather>): String? = Gson().toJson(obj, type)

  @TypeConverter
  fun toList(json: String?): List<Weather> = Gson().fromJson(json, type)
}

class WindConverter {
  private val type = object : TypeToken<Wind>() {}.type
  @TypeConverter fun toJson(data: Wind?): String {
    return Gson().toJson(data, type)
  }

  @TypeConverter fun toObj(json: String): Wind? {
    return Gson().fromJson(json, type)
  }
}

class SysConverter {
  private val type = object : TypeToken<Sys>() {}.type
  @TypeConverter fun toJson(data: Sys?): String {
    return Gson().toJson(data, type)
  }

  @TypeConverter fun toObj(json: String): Sys? {
    return Gson().fromJson(json, type)
  }
}

class MainConverter {
  private val type = object : TypeToken<Main>() {}.type
  @TypeConverter fun toJson(data: Main?): String {
    return Gson().toJson(data, type)
  }

  @TypeConverter fun toObj(json: String): Main? {
    return Gson().fromJson(json, type)
  }
}
