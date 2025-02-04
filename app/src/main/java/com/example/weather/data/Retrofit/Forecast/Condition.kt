package com.example.weather.data.Retrofit.Forecast

import com.google.gson.annotations.SerializedName

data class Condition (

	@SerializedName("text") val text : String,
	@SerializedName("icon") val icon : String,
)