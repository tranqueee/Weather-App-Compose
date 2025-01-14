package com.example.weather.data.dataManager

import com.example.weather.data.BASE_URL
import com.example.weather.data.dataManager.retrofit.GetForecast
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