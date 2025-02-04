package com.example.weather.data.Retrofit.Forecast

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetForecast {
    @GET("/v1/forecast.json?")
    suspend fun GetWeather(
        @Query("key") APIkey: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") lang: String
    ): Response<MainForecast>
}