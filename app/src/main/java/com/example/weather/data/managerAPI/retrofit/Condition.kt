package com.example.weather.data.dataManager.retrofit

import com.google.gson.annotations.SerializedName

data class Condition (

	@SerializedName("text") val text : String,
	@SerializedName("icon") val icon : String,
)