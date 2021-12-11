package com.assessment.planradar.features.home.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assessment.planradar.databinding.ItemCityBinding
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.ui.adapters.CitiesAdapter.CityViewHolder
import com.assessment.planradar.utils.layoutInflater

class CitiesAdapter(private val cityActionsListener: CityActionsListener) :
  ListAdapter<City, CityViewHolder>(callback) {

  companion object {
    private val callback = object : DiffUtil.ItemCallback<City>() {
      override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem.id == newItem.id

      override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
    val binding = ItemCityBinding.inflate(parent.layoutInflater, parent, false)
    return CityViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class CityViewHolder(private val binding: ItemCityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) {
      binding.tvCityName.text = city.toString()
      binding.layoutCity.setOnClickListener { cityActionsListener.onCityClick(city) }
      binding.ivHistory.setOnClickListener { cityActionsListener.onCityWeatherHistoryClick(city) }
    }
  }

  interface CityActionsListener {
    fun onCityClick(city: City)
    fun onCityWeatherHistoryClick(city: City)
  }
}