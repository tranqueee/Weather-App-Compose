package com.example.weather.data.Retrofit.Forecast

import com.google.gson.annotations.SerializedName

data class Astro (

	@SerializedName("sunrise") val sunrise : String,
	@SerializedName("sunset") val sunset : String,
)