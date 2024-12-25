package com.example.weather.data

import android.service.notification.Condition

data class WeatherData(
    var city : String,
    var minTemp : String,
    var maxTemp : String,
    var currentTemp : String,
    var icon : String,
    var condition : String,
    var time: String,
    var date: String
)
