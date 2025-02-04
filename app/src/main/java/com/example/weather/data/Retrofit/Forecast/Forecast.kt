package com.example.weather.data.Retrofit.Forecast

import com.google.gson.annotations.SerializedName

data class Forecast (
	@SerializedName("forecastday") val forecastday : List<Forecastday>
)