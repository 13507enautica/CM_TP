package com.example.weatherapp2.viewmodel

data class WeatherUiState (
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val temperature: Float = 0f,
    val pressure: Float = 0f,
    val windSpeed: Float = 0f,
    val windDirection: Int = 0,
    val weatherCode: Int = 0,
    val time: String = "",
)