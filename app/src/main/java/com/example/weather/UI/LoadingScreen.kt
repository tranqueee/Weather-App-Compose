package com.example.weather.UI

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ShowLoadingScreen(progress: Float) {
    Column (
        modifier = Modifier
            .background(Color(48, 63, 159, 255))
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth(0.6f))
        }
    }
}

@Composable
fun ShowErrorScreen(navController: NavController, weatherViewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .background(Color(48, 63, 159, 255))
            .fillMaxSize()
    ) {
        SetHeader(navController,weatherViewModel)
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
fun ShowSettingsScreen(navController: NavController, weatherViewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .background(Color(48, 63, 159, 255))
            .fillMaxSize()
    ) {
        IconButton(modifier = Modifier.padding(20.dp),
            onClick = {
                navController.navigate(weatherViewModel.WeatherState.value!!)
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                tint = Color(232, 234, 246, 255),
                modifier = Modifier
                    .size(50.dp)
            )
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

    }
}

@Composable
fun SetHeader(navController: NavController, weatherViewModel: WeatherViewModel) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                navController.navigate("Settings")
            }
        ) {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "",
                tint = Color(232, 234, 246, 255),
                modifier = Modifier
                    .size(50.dp)
            )
        }

        var city by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier.
            fillMaxWidth(0.8f),
            value = city,
            shape = RoundedCornerShape(20.dp),
            label = {
                Text(text = "Введите название города",
                    color = Color(232, 234, 246, 255)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(232, 234, 246, 255),
                unfocusedTextColor = Color(92, 107, 192, 255),
                focusedContainerColor = Color(48, 63, 159, 255),
                unfocusedContainerColor = Color(48, 63, 159, 255)
            ),
            onValueChange = {
                    newText ->
                city = newText
                Log.d("City","$city")
            }
        )
        Button(onClick = {
            weatherViewModel.getWeatherByCity(city, weatherViewModel.requestLanguage.value!!)
        }) {
            Text(text = "Поиск")
        }
    }
}