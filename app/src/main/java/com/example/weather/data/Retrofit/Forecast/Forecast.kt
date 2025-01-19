package com.example.weather.data.managerAPI.retrofit

import com.google.gson.annotations.SerializedName

data class Forecast (
	@SerializedName("forecastday") val forecastday : List<Forecastday>
)