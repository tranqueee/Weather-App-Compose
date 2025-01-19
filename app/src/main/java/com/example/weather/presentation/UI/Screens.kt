package com.example.weather.UI

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.weather.R
import com.example.weather.WeatherViewModel
import com.example.weather.data.Retrofit.Forecast.MainForecast


class Screens (
    private val activity: Activity,
    private val weatherViewModel: WeatherViewModel
) {
    @Composable
    fun ShowLoadingScreen() {
        Column(
            modifier = Modifier
                .background(Color(48, 63, 159, 255))
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(progress = weatherViewModel.LoadingProgress.value!!, modifier = Modifier.fillMaxWidth(0.6f))
            }
        }
    }

    @Composable
    fun ShowErrorScreen() {
        Column(
            modifier = Modifier
                .background(Color(48, 63, 159, 255))
                .fillMaxSize()
        ) {
            SetHeader()
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Ошибка")
            }
        }
    }

    @Composable
    fun ShowStartScreen() {
        Column(
            modifier = Modifier
                .background(Color(48, 63, 159, 255))
                .fillMaxSize()
        ) {
            ShowLoadingScreen()
        }
    }

    @Composable
    fun SetHeader() {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            var city by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = city,
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(
                        text = activity.getString(R.string.search_hint),
                        color = Color(232, 234, 246, 255)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color(232, 234, 246, 255),
                    unfocusedTextColor = Color(92, 107, 192, 255),
                    focusedContainerColor = Color(48, 63, 159, 255),
                    unfocusedContainerColor = Color(48, 63, 159, 255)
                ),
                onValueChange = { newText ->
                    city = newText
                }
            )
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color(92, 107, 192, 255),
                disabledContainerColor = Color(92, 107, 192, 255),
                contentColor = Color(232, 234, 246, 255),
                disabledContentColor = Color(232, 234, 246, 255)
            ),
                onClick = {
                    weatherViewModel.getForecastByCity(city)
            }) {
                Text(text = activity.getString(R.string.search_button))
            }
        }
    }

    @Composable
    fun ShowMainFragment() {
        Column(
            modifier = Modifier
                .background(Color(48, 63, 159, 255))
                .fillMaxSize()
        ) {
            val forecast = weatherViewModel.WeatherData.value!!
            SetHeader()
            CurrentWeather(forecast)
            HoursWeather(forecast)
            DaysWeather(forecast)
        }
    }

    @Composable
    fun CurrentWeather(forecast: MainForecast) {
        val temp = forecast.current.temp_c.toInt().toString()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "$temp°C",
                        fontSize = 60.sp,
                        color = Color(232, 234, 246, 255)
                    )
                    AsyncImage(
                        model = "http:${forecast.current.condition.icon}",
                        contentDescription = "",
                        alignment = Alignment.Center
                    )
                }
                Text(
                    text = forecast.current.condition.text,
                    color = Color(232, 234, 246, 255)
                )
                Text(
                    text = forecast.location.name,
                    color = Color(232, 234, 246, 255)
                )
            }
        }
    }

    @Composable
    fun HoursWeather(forecast: MainForecast) {
        val list = forecast.forecast.forecastday[0].hour
        Card(
            modifier = Modifier.padding(horizontal = 20.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color(92, 107, 192, 255))
        ) {
            LazyRow(
            ) {
                itemsIndexed(list) { _, item ->
                    val temp = item.temp_c.toInt().toString()
                    val time = item.time.removeRange(0..10)

                    Box() {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = "$temp°C",
                                    fontSize = 35.sp,
                                    color = Color(232, 234, 246, 255)
                                )
                                AsyncImage(
                                    model = "http:${item.condition.icon}",
                                    contentDescription = "",
                                    alignment = Alignment.Center
                                )
                            }
                            Text(
                                text = time,
                                color = Color(232, 234, 246, 255)
                            )
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun DaysWeather(forecast: MainForecast) {
        val list = forecast.forecast.forecastday
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color(92, 107, 192, 255))
        ) {
            LazyColumn {
                itemsIndexed(list) { index, item ->

                    val minTemp = item.day.mintemp_c.toInt().toString()
                    val maxTemp = item.day.maxtemp_c.toInt().toString()

                    val date: String

                    if (index == 0) {
                        date = stringResource(R.string.today_word)
                    } else {
                        date = item.date
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .fillMaxWidth()
                            .height(80.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            date,
                            modifier = Modifier.padding(start = 5.dp),
                            color = Color(232, 234, 246, 255)
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "$minTemp/$maxTemp°C",
                                fontSize = 35.sp,
                                color = Color(232, 234, 246, 255)
                            )
                            Row {
                                AsyncImage(
                                    model = "http:${item.day.condition.icon}",
                                    contentDescription = "",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.padding(start = 40.dp)
                                )
                                Text(
                                    text = item.day.condition.text,
                                    textAlign = TextAlign.Center,
                                    color = Color(232, 234, 246, 255)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}