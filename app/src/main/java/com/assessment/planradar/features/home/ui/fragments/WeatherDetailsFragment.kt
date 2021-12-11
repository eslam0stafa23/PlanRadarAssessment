package com.assessment.planradar.features.home.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.assessment.planradar.BuildConfig
import com.assessment.planradar.R.string
import com.assessment.planradar.databinding.FragmentWeatherDetailsBinding
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.features.home.ui.viewmodels.MainViewModel
import com.assessment.planradar.utils.Const
import com.assessment.planradar.utils.appComponent
import com.assessment.planradar.utils.result.Resource
import com.assessment.planradar.utils.result.ResourceType
import com.assessment.planradar.utils.round
import com.assessment.planradar.utils.showErrorMessage
import com.assessment.planradar.utils.showLoading
import com.assessment.planradar.utils.viewmodel.ViewModelFactory
import com.assessment.planradar.utils.views.BaseFragment
import com.bumptech.glide.Glide
import javax.inject.Inject

class WeatherDetailsFragment : BaseFragment<FragmentWeatherDetailsBinding>() {
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private val mainViewModel: MainViewModel by activityViewModels { viewModelFactory }
  private val args: WeatherDetailsFragmentArgs by navArgs()

  companion object {
    @JvmStatic
    fun show(navController: NavController, city: City) {
      val action = WeatherDetailsFragmentDirections.actionNavigateToWeatherDetailsFragment(city)
      navController.navigate(action)
    }
  }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentWeatherDetailsBinding {
    appComponent.inject(this)
    return FragmentWeatherDetailsBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setObservers()
    setClickListeners()
  }

  private fun setClickListeners() {
    binding.ivBack.setOnClickListener { navController.popBackStack() }
    binding.ivDeleteCity.setOnClickListener {
      showDeleteDialog()
    }
  }

  private fun showDeleteDialog() {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(getString(string.delete_city))
    builder.setMessage(getString(string.delete_city_dialog_description, args.city))
    builder.setPositiveButton(android.R.string.ok) { _, _ ->
      mainViewModel.removeCity(args.city)
      navController.popBackStack()
    }
    builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
      dialog.dismiss()
    }
    builder.show()
  }

  private fun setObservers() {
    mainViewModel.getCityWeather(args.city.name).observe(
      viewLifecycleOwner,
      this::handleCityWeatherData
    )
  }

  private fun handleCityWeatherData(result: Resource<WeatherData>) {
    when (result.resourceType) {
      ResourceType.LOADING -> setLoading(true)
      ResourceType.SUCCESS -> {
        setLoading(false)
        feedDataToViews(result.data)
      }
      else -> {
        setError(getString(string.generic_error))
        binding.tvLocationDate.visibility = View.GONE
      }
    }
  }

  private fun feedDataToViews(data: WeatherData?) {
    val weather = data!!.weather[0]

    binding.tvCityName.text = args.city.toString()
    binding.tvDescription.text = weather.description
    binding.tvTemperature.text =
      getString(string.temperature_value, (data.main.temp?.minus(273.15)?.round(2)).toString())
    binding.tvWindspeed.text = getString(string.windspeed_value, data.wind.speed.toString())
    binding.tvLocationDate.text =
      getString(string.weather_location_date, data.cityName, data.dateTime)

    (data.main.humidity.toString() + "%").also { binding.tvHumidity.text = it }

    Glide.with(binding.root)
      .load("${BuildConfig.ICON_URL}${weather.icon}${Const.WEATHER_ICON_SIZE_FORMAT}")
      .into(binding.ivWeatherIcon)
  }

  private fun setLoading(isLoading: Boolean) = binding.layoutProgress.showLoading(isLoading)

  private fun setError(msg: String) = binding.layoutProgress.showErrorMessage(msg)

}
