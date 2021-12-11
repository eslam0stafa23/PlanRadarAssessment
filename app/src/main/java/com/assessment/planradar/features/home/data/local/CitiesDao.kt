package com.assessment.planradar.features.home.data.local

import androidx.room.Dao
import androidx.room.Query
import com.assessment.planradar.features.home.data.models.City
import com.assessment.planradar.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CitiesDao : BaseDao<City> {
  @Query("SELECT * FROM city")
  abstract fun getAllCities(): Flow<List<City>>
}