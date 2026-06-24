package com.example.weatherapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp2.ui.WeatherScreen
import com.example.weatherapp2.ui.theme.CoolWeatherApp2Theme
import com.example.weatherapp2.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            CoolWeatherApp2Theme {
                WeatherScreen(weatherViewModel)
            }
        }
    }
}