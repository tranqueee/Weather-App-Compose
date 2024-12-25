package com.example.weather.data.dataManager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.WeatherData
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalTime

fun getWeatherData (city: String, context: Context, data: MutableState<WeatherData>, weatherDays: MutableState<ArrayList<WeatherData>>, weatherHours: MutableState<ArrayList<WeatherData>>) {
    val url = "http://api.weatherapi.com/v1/forecast.json?key=1f380f9415af41ea94e171605242312&q=$city&days=7&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)

    var currentData = WeatherData("Perm","","","","","","","")

    val request = StringRequest(Request.Method.GET, url,
        {
            response ->

            val daysData = getWeatherByDays(response)
            weatherDays.value = daysData
            val hoursData = getWeatherByHours(response)
            weatherHours.value = hoursData

            val responseObject = JSONObject(response)
            currentData.currentTemp = (responseObject.getJSONObject("current").getString("temp_c")
                .toFloat().toInt()).toString()
            currentData.icon = responseObject.getJSONObject("current").getJSONObject("condition").getString("icon")
            currentData.city = responseObject.getJSONObject("location").getString("name")
            currentData.condition = responseObject.getJSONObject("current").getJSONObject("condition").getString("text")
            currentData.minTemp = daysData.get(0).minTemp
            currentData.maxTemp = daysData.get(0).maxTemp
            data.value = currentData

        },
        {
            error ->
            Log.d("df","thats cap $error")
        }
    )
    queue.add(request)

    Log.d("DEBUG","current temp is ${data.value.currentTemp}")
    Log.d("DEBUG","current temp is ${data.value.icon}")
}

fun getWeatherByDays (response: String): ArrayList<WeatherData> {
    
    val dataObject = JSONObject(response)
    val city = dataObject.getJSONObject("location").getString("name")
    val arrayData = dataObject.getJSONObject("forecast").getJSONArray("forecastday")
    val dataByDays = ArrayList<WeatherData>()

    for (i in 0 until arrayData.length()) {
        val day = arrayData[i] as JSONObject
        dataByDays.add(i, WeatherData(
            city = city,
            minTemp = (day.getJSONObject("day").getString("maxtemp_c")
                .toFloat().toInt()).toString(),
            maxTemp = (day.getJSONObject("day").getString("mintemp_c")
                .toFloat().toInt()).toString(),
            currentTemp = "",
            icon = day.getJSONObject("day").getJSONObject("condition").getString("icon"),
            condition = day.getJSONObject("day").getJSONObject("condition").getString("text"),
            time = "",
            date = day.getString("date"),
        )
        )
    }

    return dataByDays
}

@SuppressLint("NewApi")
fun getWeatherByHours (response: String): ArrayList<WeatherData> {
    
    val dataObject = JSONObject(response)
    val arrayData = dataObject.getJSONObject("forecast").getJSONArray("forecastday")
    val currentDay = arrayData[0] as JSONObject
    val hours = currentDay.getJSONArray("hour")
    val dataByHours = ArrayList<WeatherData>()
    
    for (i in 0 until hours.length()) {
        val item = hours[i] as JSONObject
        val date = item.getString("time")
        val time = date.replaceRange(0..10,"")
        
        dataByHours.add(i, WeatherData(
            city = "",
            minTemp = "",
            maxTemp = "",
            currentTemp = (item.getString("temp_c")
                .toFloat().toInt()).toString(),
            icon = item.getJSONObject("condition").getString("icon"),
            condition = item.getJSONObject("condition").getString("text"),
            time = time,
            date = ""
        ))
    }
    
    return dataByHours
}