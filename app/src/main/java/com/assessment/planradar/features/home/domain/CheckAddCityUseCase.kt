package com.assessment.planradar.features.home.domain

import com.assessment.planradar.utils.result.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckAddCityUseCase @Inject constructor(private val mainRepository: MainRepository) {
  fun execute(cityName: String): Flow<Resource<Any>> =
    mainRepository.checkAddCity(cityName)
}