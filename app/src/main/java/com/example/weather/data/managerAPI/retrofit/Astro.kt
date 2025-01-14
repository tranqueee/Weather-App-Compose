package com.example.weather.data.dataManager.retrofit

import com.google.gson.annotations.SerializedName

data class Astro (

	@SerializedName("sunrise") val sunrise : String,
	@SerializedName("sunset") val sunset : String,
)