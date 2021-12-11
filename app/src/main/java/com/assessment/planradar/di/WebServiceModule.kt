package com.assessment.planradar.di

import com.assessment.planradar.BuildConfig
import com.assessment.planradar.features.home.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebServiceModule {
  @Singleton @Provides
  fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.MINUTES)
      .readTimeout(1, TimeUnit.MINUTES)
      .addInterceptor(logging)
      .build()
  }

  private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.MAIN_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton @Provides
  fun provideMainRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return buildRetrofit(okHttpClient)
  }

  @Provides @Singleton
  fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
    return retrofit.create(WeatherApiService::class.java)
  }
}