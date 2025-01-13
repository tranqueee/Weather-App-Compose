package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.UI.ShowErrorScreen
import com.example.weather.UI.ShowLoadingScreen
import com.example.weather.UI.ShowMainFragment
import com.example.weather.UI.ShowSettingsScreen
import com.example.weather.UI.ShowStartScreen
import com.example.weather.UI.WeatherViewModel
import com.example.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherViewModel.setRequestLanguage(getString(R.string.request_language))
        weatherViewModel.getWeatherByLocation(this)

        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                weatherViewModel.WeatherState.observe(this, Observer {
                    weatherViewModel.WeatherState.value?.let { it1 -> navController.navigate(it1) }
                })
                NavHost(navController = navController, startDestination = "Start") {
                    composable("Loaded") {
                        ShowMainFragment(navController,weatherViewModel)
                    }
                    composable("Loading") {
                        ShowLoadingScreen(weatherViewModel.LoadingProgress.value!!)
                    }
                    composable("Settings") {
                        ShowSettingsScreen(navController, weatherViewModel)
                    }
                    composable("Start") {
                        ShowStartScreen()
                    }
                    composable("Error") {
                        ShowErrorScreen(navController, weatherViewModel)
                    }
                }
            }
        }
    }
}