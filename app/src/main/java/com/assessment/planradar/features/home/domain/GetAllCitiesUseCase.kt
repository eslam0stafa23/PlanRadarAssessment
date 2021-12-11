package com.assessment.planradar.features.home.domain

import com.assessment.planradar.features.home.data.models.City
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCitiesUseCase @Inject constructor(private val mainRepository: MainRepository) {
  fun get(): Flow<List<City>> = mainRepository.getAllCities()
}