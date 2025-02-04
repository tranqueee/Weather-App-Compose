package com.example.weather.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.weather.R
import com.example.weather.presentation.ResponseState
import com.example.weather.data.Retrofit.Forecast.GetForecast
import com.example.weather.data.Retrofit.Forecast.MainForecast
import com.example.weather.models.ForecastRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val response: GetForecast,
    private val fusedLocationProviderClient: FusedLocationProviderClient
): ForecastRepository {

    private val requestLanguage = context.getString(R.string.request_language)

    override fun getForecastByLocation(weatherData: MutableLiveData<MainForecast>, weatherState: MutableLiveData<ResponseState>) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            weatherState.value = ResponseState.Error("Location is not enabled!")
        }
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,CancellationTokenSource().token).addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
                response.GetWeather(KEY_API, "${it.latitude}, ${it.longitude}", 3, requestLanguage).body().let {
                    CoroutineScope(Dispatchers.Main).launch {
                        weatherData.value = it
                        weatherState.value = ResponseState.Loaded(it!!)
                    }
                }
            }
        }
    }

    override suspend fun getForecastByCity(location: String): MainForecast? = response.GetWeather(KEY_API,location,3,requestLanguage).body()
}