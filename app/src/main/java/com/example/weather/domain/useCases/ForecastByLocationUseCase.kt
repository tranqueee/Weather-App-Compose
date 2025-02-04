package com.example.weather.domain.useCases

import androidx.lifecycle.MutableLiveData
import com.example.weather.presentation.ResponseState
import com.example.weather.data.Retrofit.Forecast.MainForecast
import com.example.weather.models.ForecastRepository
import javax.inject.Inject

class ForecastByLocationUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    fun get(weatherData: MutableLiveData<MainForecast>, weatherState: MutableLiveData<ResponseState>) = forecastRepository.getForecastByLocation(weatherData, weatherState)
}