package com.example.weather.data.dataManager

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.example.weather.data.WeatherData
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun GetWeatherData (location: String, context: Context, weatherDays: MutableState<ArrayList<WeatherData>>, weatherHours: MutableState<ArrayList<WeatherData>>) {
    val url = "http://api.weatherapi.com/v1/forecast.json?key=1f380f9415af41ea94e171605242312&q=$location&days=7&aqi=no&alerts=no&lang=${stringResource(R.string.request_language)}"
    val queue = Volley.newRequestQueue(context)

    val request = StringRequest(Request.Method.GET, url,
        {
            response ->

            val responseArray = response.toByteArray(Charsets.ISO_8859_1)
            val newResponse = String(responseArray,Charsets.UTF_8)

            weatherDays.value = getWeatherByDays(newResponse)
            weatherHours.value = getWeatherByHours(newResponse)

        },
        {
            error ->
            Log.d("HTTP REQUEST","$error")
        }
    )
    queue.add(request)

}

fun getWeatherByDays (response: String): ArrayList<WeatherData> {

    val responseArray = response.toByteArray(Charsets.UTF_8)
    val newResponse = responseArray.decodeToString()
    
    val dataObject = JSONObject(newResponse)
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
        ))
        if (i == 0) {
            dataByDays[0].currentTemp = (dataObject.getJSONObject("current").getString("temp_c")
                .toFloat().toInt()).toString()
        }
    }

    return dataByDays
}

fun getWeatherByHours (response: String): ArrayList<WeatherData> {
    
    val dataObject = JSONObject(response)
    val arrayData = dataObject.getJSONObject("forecast").getJSONArray("forecastday")

    val currentDay = arrayData[0] as JSONObject
    val hoursCurrent = currentDay.getJSONArray("hour")

    val nextDay = arrayData[1] as JSONObject
    val hoursNextDay = nextDay.getJSONArray("hour")

    val dataByHours = ArrayList<WeatherData>()
    
    for (i in 0 until hoursCurrent.length()) {
        val item = hoursCurrent[i] as JSONObject
        val date = item.getString("time")

        val time = date.replaceRange(0..10,"")
        val itemHour = time.replaceRange(2..4,"").toInt()

        val currentHour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")).toInt()

        if (itemHour >= currentHour) {
            dataByHours.add(
                WeatherData(
                    city = "",
                    minTemp = "",
                    maxTemp = "",
                    currentTemp = (item.getString("temp_c")
                        .toFloat().toInt()).toString(),
                    icon = item.getJSONObject("condition").getString("icon"),
                    condition = item.getJSONObject("condition").getString("text"),
                    time = time,
                    date = ""
                )
            )
        }
    }

    if (dataByHours.size < 24) {
        for (i in 0 until 24-dataByHours.size) {
            val item = hoursNextDay[i] as JSONObject
            val date = item.getString("time")
            val time = date.replaceRange(0..10,"")

            dataByHours.add(
                WeatherData(
                    city = "",
                    minTemp = "",
                    maxTemp = "",
                    currentTemp = (item.getString("temp_c")
                        .toFloat().toInt()).toString(),
                    icon = item.getJSONObject("condition").getString("icon"),
                    condition = item.getJSONObject("condition").getString("text"),
                    time = time,
                    date = ""
                )
            )
        }
    }
    
    return dataByHours
}