package com.example.weather.data.Retrofit.Forecast

import com.google.gson.annotations.SerializedName

data class Current (

	@SerializedName("last_updated_epoch") val last_updated_epoch : Int,
	@SerializedName("last_updated") val last_updated : String,
	@SerializedName("temp_c") val temp_c : Double,
	@SerializedName("is_day") val is_day : Int,
	@SerializedName("condition") val condition : Condition,
	@SerializedName("wind_mph") val wind_mph : Double,
	@SerializedName("wind_kph") val wind_kph : Double,
	@SerializedName("wind_dir") val wind_dir : String,
	@SerializedName("pressure_mb") val pressure_mb : Int,
	@SerializedName("pressure_in") val pressure_in : Double,
	@SerializedName("humidity") val humidity : Int,
	@SerializedName("cloud") val cloud : Int,
	@SerializedName("feelslike_c") val feelslike_c : Double,
	@SerializedName("vis_km") val vis_km : Double,
	@SerializedName("vis_miles") val vis_miles : Double,
	@SerializedName("uv") val uv : Double,
)