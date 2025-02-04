package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.presentation.ui.Screens
import com.example.weather.presentation.ResponseState
import com.example.weather.presentation.WeatherViewModel
import com.example.weather.presentation.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                weatherViewModel.getForecastByLocation()

                val navController = rememberNavController()
                val screens = Screens(this, weatherViewModel)

                weatherViewModel.WeatherState.observe(this) {
                    when (weatherViewModel.WeatherState.value) {
                        ResponseState.Empty -> navController.navigate("Start")
                        is ResponseState.Error -> navController.navigate("Error")
                        is ResponseState.Loaded -> navController.navigate("Loaded")
                        ResponseState.Loading -> navController.navigate("Loading")
                        null -> weatherViewModel.getForecastByCity("Moscow")
                    }
                }


                NavHost(navController = navController, startDestination = "Start") {
                    composable("Loaded") {
                        screens.ShowMainFragment()
                    }
                    composable("Loading") {
                        screens.ShowLoadingScreen()
                    }
                    composable("Start") {
                        screens.ShowStartScreen()
                    }
                    composable("Error") {
                        screens.ShowErrorScreen()
                    }
                }
            }
        }
    }
}