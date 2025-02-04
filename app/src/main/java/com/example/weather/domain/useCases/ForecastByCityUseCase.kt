package com.example.weather.domain.useCases

import com.example.weather.data.Retrofit.Forecast.MainForecast
import com.example.weather.models.ForecastRepository

class ForecastByCityUseCase(
    private val forecastRepository: ForecastRepository
) {
    suspend fun get(location: String): MainForecast? = forecastRepository.getForecastByCity(location)
}