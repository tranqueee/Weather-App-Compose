package com.example.weather.UI

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.KEY_API
import com.example.weather.data.dataManager.ManagerAPI
import com.example.weather.data.dataManager.retrofit.MainForecast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch

class WeatherViewModel(): ViewModel() {
    private val response = ManagerAPI().response

    private val mWeatherState: MutableLiveData<String> = MutableLiveData("")
    val WeatherState: LiveData<String> = mWeatherState

    private val mWeatherData: MutableLiveData<MainForecast> = MutableLiveData()
    val WeatherData: LiveData<MainForecast> = mWeatherData

    private val mLoadingProgress: MutableLiveData<Float> = MutableLiveData(0.0f)
    val LoadingProgress: LiveData<Float> = mLoadingProgress

    val mRequestLanguage: MutableLiveData<String> = MutableLiveData("")
    val requestLanguage: LiveData<String> = mRequestLanguage

    fun getWeatherByLocation(activity: Activity) {
        mWeatherState.value = "Loading"
        val fusedProvider = LocationServices.getFusedLocationProviderClient(activity)
        mLoadingProgress.value = 0.0f

        if (ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
        } else {
            mLoadingProgress.value = 0.3f
            fusedProvider.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener {
                mLoadingProgress.value = 0.6f
                viewModelScope.launch {
                    val location = "${it.latitude},${it.longitude}"
                    val responseAPI = response.GetWeather(KEY_API,location,3,requestLanguage.value!!)
                    mLoadingProgress.value = 0.8f
                    if (responseAPI.isSuccessful) {
                        mLoadingProgress.value = 1.0f
                        responseAPI.body()?.let {
                            mWeatherState.value = "Loaded"
                            mWeatherData.value = it
                        }
                    } else {
                        mWeatherState.value = "Error"
                    }
                }

            }
        }
    }

    fun getWeatherByCity(location: String, language: String) {
        mLoadingProgress.value = 0.0f
        viewModelScope.launch {
            mWeatherState.value = "Loading"
            try {
                val responseAPI = response.GetWeather(KEY_API, location, 3, language)
                if (responseAPI.isSuccessful) {
                    responseAPI.body()?.let {
                        mWeatherState.value = "Loaded"
                        mWeatherData.value = it
                    }
                } else {
                    mWeatherState.value = "Error"
                }
            } catch (e: Exception) {
                mWeatherState.value = "Error"
            }
        }
    }

    fun setRequestLanguage(requestLang: String) {
        mRequestLanguage.value = requestLang
    }
}