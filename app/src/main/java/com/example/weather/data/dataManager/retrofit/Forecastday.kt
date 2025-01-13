package com.example.weather.data.dataManager.retrofit

import com.google.gson.annotations.SerializedName

data class Forecastday (

	@SerializedName("date") val date : String,
	@SerializedName("date_epoch") val date_epoch : Int,
	@SerializedName("day") val day : Day,
	@SerializedName("astro") val astro : Astro,
	@SerializedName("hour") val hour : List<Hour>
)