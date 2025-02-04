package com.example.weather.data.Retrofit.Forecast

import com.google.gson.annotations.SerializedName

data class MainForecast(
	@SerializedName("location") val location : Location,
	@SerializedName("current") val current : Current,
	@SerializedName("forecast") val forecast : Forecast
)
