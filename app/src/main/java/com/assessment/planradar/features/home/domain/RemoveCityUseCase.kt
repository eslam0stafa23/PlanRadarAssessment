package com.assessment.planradar.features.home.domain

import com.assessment.planradar.features.home.data.models.City
import javax.inject.Inject

class RemoveCityUseCase @Inject constructor(private val mainRepository: MainRepository) {
  suspend fun execute(city: City) = mainRepository.removeCity(city)
}