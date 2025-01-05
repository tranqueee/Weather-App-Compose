package com.example.weather.UI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.weather.R
import com.example.weather.data.WeatherData

@Composable
fun CurrentWeather (item: WeatherData) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
        contentAlignment = Alignment.Center
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
                    text = "${item.currentTemp}°C",
                    fontSize = 60.sp,
                    color = Color(232, 234, 246, 255)
                )
                AsyncImage(
                    model = "http:${item.icon}",
                    contentDescription = "",
                    alignment = Alignment.Center,
                )
            }
            Text(
                text = "${item.minTemp}/${item.maxTemp}°C",
                color = Color(232, 234, 246, 255),
                fontSize = 25.sp
            )
            Text(
                text = item.condition,
                color = Color(232, 234, 246, 255)
            )
            Text(
                text = item.city,
                color = Color(232, 234, 246, 255)
            )
        }
    }
}

@Composable
fun HoursWeather (list: MutableState<ArrayList<WeatherData>>) {

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
fun DaysWeather (list: MutableState<ArrayList<WeatherData>>) {

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

                val date : String

                if (index == 0) {
                    date = stringResource(R.string.today_word)
                } else {
                    date = item.date.replace("-",".")
                }

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