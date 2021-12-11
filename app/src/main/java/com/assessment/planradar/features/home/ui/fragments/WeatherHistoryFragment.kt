package com.assessment.planradar.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.planradar.R
import com.assessment.planradar.databinding.FragmentWeatherHistoryBinding
import com.assessment.planradar.features.home.data.models.WeatherData
import com.assessment.planradar.features.home.ui.adapters.WeathersAdapter
import com.assessment.planradar.features.home.ui.viewmodels.MainViewModel
import com.assessment.planradar.utils.appComponent
import com.assessment.planradar.utils.result.Resource
import com.assessment.planradar.utils.result.ResourceType
import com.assessment.planradar.utils.showErrorMessage
import com.assessment.planradar.utils.showLoading
import com.assessment.planradar.utils.viewmodel.ViewModelFactory
import com.assessment.planradar.utils.views.BaseFragment
import javax.inject.Inject

class WeatherHistoryFragment : BaseFragment<FragmentWeatherHistoryBinding>() {
  @Inject lateinit var viewModelFactory: ViewModelFactory
  private val mainViewModel: MainViewModel by activityViewModels { viewModelFactory }
  private val weathersAdapter: WeathersAdapter by lazy { WeathersAdapter() }
  private val args: WeatherHistoryFragmentArgs by navArgs()

  companion object {
    @JvmStatic
    fun show(navController: NavController, cityName: String?) {
      val action = WeatherHistoryFragmentDirections.actionNavigateToWeatherHistoryFragment(cityName)
      navController.navigate(action)
    }
  }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentWeatherHistoryBinding {
    appComponent.inject(this)
    return FragmentWeatherHistoryBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()
    setObservers()
    setClickListeners()
  }

  private fun setClickListeners() {
    binding.ivBack.setOnClickListener { navController.popBackStack() }
  }

  private fun setupViews() {
    binding.rvHistory.layoutManager = LinearLayoutManager(context)
    binding.rvHistory.adapter = weathersAdapter
    binding.tvCityName.text = getString(R.string.city_name_history, args.cityName)
  }

  private fun setObservers() {
    mainViewModel.getCityWeatherHistory(args.cityName!!).observe(
      viewLifecycleOwner,
      this::handleHistoryData
    )
  }

  private fun handleHistoryData(result: Resource<List<WeatherData>>) {
    when (result.resourceType) {
      ResourceType.LOADING -> setLoading(true)
      ResourceType.SUCCESS -> {
        setLoading(false)
        submitHistoryList(result.data)
      }
      ResourceType.EMPTY_DATA -> {
        binding.tvNoHistory.visibility = View.VISIBLE
        setLoading(false)
      }
      else -> {
        binding.tvNoHistory.visibility = View.GONE
        setError(getString(R.string.generic_error))
      }
    }
  }

  private fun setLoading(isLoading: Boolean) = binding.layoutProgress.showLoading(isLoading)

  private fun setError(msg: String) = binding.layoutProgress.showErrorMessage(msg)

  private fun submitHistoryList(list: List<WeatherData>?) = weathersAdapter.submitList(list)

}
