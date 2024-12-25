package com.example.weather

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.weather.data.WeatherData
import com.example.weather.data.dataManager.getWeatherData
import com.example.weather.ui.theme.WeatherTheme


@Composable
fun MainCard (item : MutableState<WeatherData>) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 135.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "${item.value.currentTemp}°C",
                    fontSize = 60.sp,
                    color = Color(232, 234, 246, 255)
                )
                AsyncImage(
                    model = "http:${item.value.icon}",
                    contentDescription = "",
                    alignment = Alignment.Center,
                )
            }
            Text(
                text = "${item.value.minTemp}/${item.value.maxTemp}°C",
                color = Color(232, 234, 246, 255),
                fontSize = 25.sp
            )
            Text(
                text = item.value.condition,
                color = Color(232, 234, 246, 255)
            )
            Text(
                text = item.value.city,
                color = Color(232, 234, 246, 255)
            )
        }
    }
}

@Composable
fun hourWeather (list: MutableState<ArrayList<WeatherData>>) {

    Card (
        modifier = Modifier.padding(horizontal = 20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(92, 107, 192, 255))
    ) {
        LazyRow(
        ) {
            itemsIndexed(list.value) { index, item ->
                Box(

                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "${item.currentTemp}°C",
                                fontSize = 35.sp,
                                color = Color(232, 234, 246, 255)
                            )
                            AsyncImage(
                                model = "http:${item.icon}",
                                contentDescription = "",
                                alignment = Alignment.Center
                            )
                        }
                        Text(text = item.time,
                            color = Color(232, 234, 246, 255)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun daysWeather (list: MutableState<ArrayList<WeatherData>>) {

    Card (
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(92, 107, 192, 255))
    ) {
        LazyColumn {
            itemsIndexed(list.value) {
                index, item ->

                var date : String

                if (index == 0) {
                    date = "Today"
                } else {
                    date = item.date.replace("-",".")
                }

                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row (
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
                            color = Color(232, 234, 246, 255),
                        )
                        Column (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "${item.minTemp}/${item.maxTemp}°C",
                                fontSize = 35.sp,
                                color = Color(232, 234, 246, 255)
                            )
                            Row {
                                AsyncImage(
                                    model = "http:${item.icon}",
                                    contentDescription = "",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.padding(start = 40.dp)
                                )
                                Text(
                                    text = item.condition,
                                    textAlign = TextAlign.Center,
                                    color = Color(232, 234, 246, 255),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}