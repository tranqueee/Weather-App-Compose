package com.example.weather.data.Retrofit.Forecast

import com.google.gson.annotations.SerializedName

data class Day (

	@SerializedName("maxtemp_c") val maxtemp_c : Double,
	@SerializedName("mintemp_c") val mintemp_c : Double,
	@SerializedName("condition") val condition : Condition
)