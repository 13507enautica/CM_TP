package com.example.weatherapp2.data

import kotlinx.serialization.Serializable


@Serializable
data class WeatherData (
    var latitude:Float,
    var longitude:Float,
    var current_weather:CurrentWeather,
    var hourly:Hourly
)

@Serializable
data class CurrentWeather (
    var temperature:Float,
    var windspeed:Float,
    var winddirection:Int,
    var weathercode:Int,
    var time:String
)

@Serializable
data class Hourly (
    var time:ArrayList<String> ,
    var temperature_2m:ArrayList<Float>,
    var weathercode:ArrayList<Float>,
    var pressure_msl:ArrayList<Float>
)