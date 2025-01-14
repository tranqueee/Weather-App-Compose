package com.example.weather.data.dataManager.retrofit

import com.google.gson.annotations.SerializedName

data class Forecast (
	@SerializedName("forecastday") val forecastday : List<Forecastday>
)