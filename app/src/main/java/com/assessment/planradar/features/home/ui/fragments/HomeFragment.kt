package com.assessment.planradar.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.planradar.R
import com.assessment.planradar.databinding.FragmentHomeBinding
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.features.home.ui.adapters.CitiesAdapter
import com.assessment.planradar.features.home.ui.adapters.CitiesAdapter.CityActionsListener
import com.assessment.planradar.features.home.ui.viewmodels.MainViewModel
import com.assessment.planradar.utils.appComponent
import com.assessment.planradar.utils.result.Resource
import com.assessment.planradar.utils.result.ResourceType
import com.assessment.planradar.utils.showErrorMessage
import com.assessment.planradar.utils.showLoading
import com.assessment.planradar.utils.viewmodel.ViewModelFactory
import com.assessment.planradar.utils.views.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(), CityActionsListener {
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private val mainViewModel: MainViewModel by activityViewModels { viewModelFactory }
  private val citiesAdapter: CitiesAdapter by lazy { CitiesAdapter(this) }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentHomeBinding {
    appComponent.inject(this)
    return FragmentHomeBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupCitiesRecyclerView()
    setObservers()
    setClickListeners()
  }

  private fun setClickListeners() {
    binding.fabAddCity.setOnClickListener {
      AddCityBottomSheet.show(navController)
    }
  }

  private fun setupCitiesRecyclerView() {
    binding.rvCities.layoutManager = LinearLayoutManager(context)
    binding.rvCities.adapter = citiesAdapter
  }

  private fun setObservers() {
    mainViewModel.getAllCities().observe(viewLifecycleOwner, this::handleCitiesResult)
  }

  private fun handleCitiesResult(result: Resource<List<City>>) {
    when (result.resourceType) {
      ResourceType.LOADING -> setLoading(true)
      ResourceType.SUCCESS -> {
        setLoading(false)
        binding.tvNoCities.visibility = View.GONE
        submitCityList(result.data)
      }
      ResourceType.EMPTY_DATA -> {
        binding.tvNoCities.visibility = View.VISIBLE
        submitCityList(null)
        setLoading(false)
      }
      else -> {
        binding.tvNoCities.visibility = View.GONE
        setError(getString(R.string.generic_error))
      }
    }
  }

  private fun setLoading(isLoading: Boolean) = binding.layoutProgress.showLoading(isLoading)

  private fun setError(msg: String) = binding.layoutProgress.showErrorMessage(msg)

  private fun submitCityList(list: List<City>?) = citiesAdapter.submitList(list)

  override fun onCityClick(city: City) {
    WeatherDetailsFragment.show(navController, city)
  }

  override fun onCityWeatherHistoryClick(city: City) {
    WeatherHistoryFragment.show(navController, city.name)
  }

}
