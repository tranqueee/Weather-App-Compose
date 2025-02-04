package com.example.weather.data.Retrofit

import com.example.weather.data.BASE_URL
import com.example.weather.data.Retrofit.Forecast.GetForecast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManagerAPI {

    fun getWeatherData():Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    var response:GetForecast = getWeatherData().create(GetForecast::class.java)
}