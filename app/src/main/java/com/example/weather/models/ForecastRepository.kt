package com.example.weather.models

import androidx.lifecycle.MutableLiveData
import com.example.weather.presentation.ResponseState
import com.example.weather.data.Retrofit.Forecast.MainForecast

interface ForecastRepository {

    fun getForecastByLocation(weatherData: MutableLiveData<MainForecast> ,weatherState: MutableLiveData<ResponseState>)

    suspend fun getForecastByCity(location: String): MainForecast?
}