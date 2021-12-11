package com.assessment.planradar.features.home.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assessment.planradar.BuildConfig
import com.assessment.planradar.databinding.ItemWeatherHistoryBinding
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.features.home.ui.adapters.WeathersAdapter.WeatherViewHolder
import com.assessment.planradar.utils.Const
import com.assessment.planradar.utils.layoutInflater
import com.assessment.planradar.utils.round
import com.bumptech.glide.Glide

class WeathersAdapter : ListAdapter<WeatherData, WeatherViewHolder>(callback) {

  companion object {
    private val callback = object : DiffUtil.ItemCallback<WeatherData>() {
      override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData) =
        oldItem.weatherId == newItem.weatherId

      override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData) =
        oldItem == newItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
    val binding = ItemWeatherHistoryBinding.inflate(parent.layoutInflater, parent, false)
    return WeatherViewHolder(binding)
  }

  override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class WeatherViewHolder(private val binding: ItemWeatherHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: WeatherData) {
      val weather = data.weather[0]

      ("${weather.description}, ${data.main.temp?.minus(273.15)?.round(2).toString()} C")
        .also { binding.tvDescriptionTemp.text = it }
      binding.tvDateTime.text = data.dateTime
      Glide.with(binding.root)
        .load("${BuildConfig.ICON_URL}${weather.icon}${Const.WEATHER_ICON_SIZE_FORMAT}")
        .into(binding.ivWeatherIcon)

    }
  }

}