package com.example.weather.data.managerAPI.retrofit

import com.google.gson.annotations.SerializedName

data class Location (

	@SerializedName("name") val name : String,
	@SerializedName("region") val region : String,
	@SerializedName("country") val country : String,
	@SerializedName("tz_id") val tz_id : String,
	@SerializedName("localtime_epoch") val localtime_epoch : Int,
	@SerializedName("localtime") val localtime : String
)