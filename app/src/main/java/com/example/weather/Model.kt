package com.example.weather

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.example.weather.data.KEY_API
import com.example.weather.data.managerAPI.ManagerAPI
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Model(private val activity: Activity) {
    private val response = ManagerAPI().response
    val fusedProvider = LocationServices.getFusedLocationProviderClient(activity)

    fun getForecastByLocation(viewModel: WeatherViewModel) {
        if (ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
        } else {
            fusedProvider.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener {
                val location = "${it.latitude},${it.longitude}"
                CoroutineScope(Dispatchers.IO).launch {
                    val responseAPI = response.GetWeather(KEY_API,location,3,viewModel.requestLanguage.value!!)
                    if (responseAPI.isSuccessful) {
                        responseAPI.body()?.let {
                            viewModel.setWeatherState("Loaded")
                            viewModel.setWeatherData(it)
                        }
                    } else {
                        viewModel.setWeatherState("Error")
                    }
                }
            }
        }
    }

    fun getWeatherByCity(location: String, viewModel: WeatherViewModel) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setWeatherState("Loading")
            try {
                val responseAPI = response.GetWeather(KEY_API, location, 3, viewModel.requestLanguage.value!!)
                if (responseAPI.isSuccessful) {
                    responseAPI.body()?.let {
                        viewModel.setWeatherState("Loaded")
                        viewModel.setWeatherData(it)
                    }
                } else {
                    viewModel.setWeatherState("Error")
                }
            } catch (e: Exception) {
                viewModel.setWeatherState("Error")
            }
        }
    }
}