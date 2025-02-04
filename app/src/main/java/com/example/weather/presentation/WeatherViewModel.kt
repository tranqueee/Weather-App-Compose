package com.example.weather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Retrofit.Forecast.MainForecast
import com.example.weather.domain.useCases.ForecastByCityUseCase
import com.example.weather.domain.useCases.ForecastByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val forecastByLocation: ForecastByLocationUseCase,
    private val forecastByCity: ForecastByCityUseCase,
): ViewModel() {

    private val mWeatherState: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.Empty)
    val WeatherState: LiveData<ResponseState> = mWeatherState

    private val mWeatherData: MutableLiveData<MainForecast> = MutableLiveData()
    val WeatherData: LiveData<MainForecast> = mWeatherData

    private val mLoadingProgress: MutableLiveData<Float> = MutableLiveData(0.0f)
    val LoadingProgress: LiveData<Float> = mLoadingProgress


    fun getForecastByCity(city: String) {
        mWeatherState.value = ResponseState.Loading
        viewModelScope.launch {
            forecastByCity.get(city).let {
                if (it != null) {
                    mWeatherState.value = ResponseState.Loaded(it)
                    mWeatherData.value = it
                } else {
                    mWeatherState.value = ResponseState.Error("Service error")
                }
            }
        }
    }

    fun getForecastByLocation() {
        mWeatherState.value = ResponseState.Loading
        forecastByLocation.get(mWeatherData, mWeatherState)
    }

}

sealed class ResponseState {
    data object Loading : ResponseState()
    class Loaded(val data: MainForecast) : ResponseState()
    data object Empty : ResponseState()
    class Error(val message: String) : ResponseState()
}