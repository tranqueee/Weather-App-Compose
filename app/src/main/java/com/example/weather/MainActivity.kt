package com.example.weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.WeatherData
import com.example.weather.data.dataManager.getWeatherData
import com.example.weather.ui.theme.WeatherTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                Column (
                    modifier = Modifier
                        .background(Color(48, 63, 159, 255))
                        .fillMaxSize()
                ) {
                    val currentWeatner = remember { mutableStateOf(WeatherData("Perm","","","--","","","","")) }
                    val weatherDays = remember { mutableStateOf(ArrayList<WeatherData>()) }
                    val weatherHours = remember { mutableStateOf(ArrayList<WeatherData>()) }

                    getWeatherData("Perm",this@MainActivity,currentWeatner, weatherDays, weatherHours)
                    MainCard(currentWeatner)
                    hourWeather(weatherHours)
                    daysWeather(weatherDays)
                }
            }
        }
    }
}

fun getCurrentWeather (city: String, context: Context, data: MutableState<WeatherData>) {
    val url = "http://api.weatherapi.com/v1/forecast.json?key=1f380f9415af41ea94e171605242312&q=$city&days=7&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)

    var currentData = WeatherData("Perm","","","","","","", "")

    val request = StringRequest(
        Request.Method.GET, url,
        {
                response ->
            val responseObject = JSONObject(response)
            data.value.currentTemp = responseObject.getJSONObject("current").getString("temp_c")
            data.value.icon = responseObject.getJSONObject("current").getJSONObject("condition").getString("icon")
        },
        {
                error ->
            Log.d("df","thats cap $error")
        }
    )
    queue.add(request)

    Log.d("DEBUG","current temp is ${data.value.currentTemp}")
}
