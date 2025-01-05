package com.example.weather

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.weather.data.WeatherData
import com.example.weather.data.dataManager.GetWeatherData
import com.example.weather.UI.CurrentWeather
import com.example.weather.UI.DaysWeather
import com.example.weather.UI.HoursWeather
import com.example.weather.ui.theme.WeatherTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        isLocationEnabled()

        setContent {
            WeatherTheme {
                Column (
                    modifier = Modifier
                        .background(Color(48, 63, 159, 255))
                        .fillMaxSize()
                ) {
                    val currentLocation = remember {mutableStateOf("")}
                    getLocationData(currentLocation, this@MainActivity)

                    val weatherDays = remember {mutableStateOf(arrayListOf(WeatherData(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""
                    )))
                    }
                    val weatherHours = remember {mutableStateOf(ArrayList<WeatherData>())}
                    GetWeatherData(currentLocation.value,
                        this@MainActivity,
                        weatherDays,
                        weatherHours
                    )

                    CurrentWeather(weatherDays.value[0])
                    HoursWeather(weatherHours)
                    DaysWeather(weatherDays)
                }
            }
        }
    }

    private fun isLocationEnabled() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
        }
    }

    private fun getLocationData(currentLocation: MutableState<String>, context: Context) {
        val fusedProvider = LocationServices.getFusedLocationProviderClient(context)

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            isLocationEnabled()
        } else {
            fusedProvider.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnCompleteListener {
                currentLocation.value = "${it.result.latitude}, ${it.result.longitude}"
            }
        }
    }
}