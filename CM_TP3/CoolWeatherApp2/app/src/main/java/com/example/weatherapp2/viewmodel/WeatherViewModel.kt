package com.example.weatherapp2.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp2.data.WeatherApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun fetchWeather(lat:Float, long:Float) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                latitude = lat,
                longitude = long,
            )

            val weather = withContext(Dispatchers.IO) {
                WeatherApiClient.getWeather(lat, long)
            }

            _uiState.value = _uiState.value.copy(
                temperature = weather.current_weather.temperature,
                pressure = weather.hourly.pressure_msl,
                windSpeed = weather.current_weather.windspeed,
                windDirection = weather.current_weather.winddirection,
                time = weather.current_weather.time,
                weatherCode = weather.current_weather.weathercode
            )
        }
    }
}