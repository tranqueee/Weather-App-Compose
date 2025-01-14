package com.example.weather.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.managerAPI.retrofit.MainForecast

class WeatherViewModel(): ViewModel() {
    private val mWeatherState: MutableLiveData<String> = MutableLiveData("Start")
    val WeatherState: LiveData<String> = mWeatherState

    private val mWeatherData: MutableLiveData<MainForecast> = MutableLiveData()
    val WeatherData: LiveData<MainForecast> = mWeatherData

    private val mLoadingProgress: MutableLiveData<Float> = MutableLiveData(0.0f)
    val LoadingProgress: LiveData<Float> = mLoadingProgress

    val mRequestLanguage: MutableLiveData<String> = MutableLiveData("")
    val requestLanguage: LiveData<String> = mRequestLanguage
    
    fun setWeatherState(state: String) {
        mWeatherState.postValue(state)
    }
    
    fun setWeatherData(weatherData: MainForecast) {
        mWeatherData.postValue(weatherData)
    }

    fun setRequestLanguage(requestLang: String) {
        mRequestLanguage.value = requestLang
    }
}