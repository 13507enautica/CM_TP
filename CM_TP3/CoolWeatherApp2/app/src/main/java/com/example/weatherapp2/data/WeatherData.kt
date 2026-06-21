package com.example.weatherapp2.data

data class WeatherData (
    var latitude:Float,
    var longitude:Float,
    var current_weather:CurrentWeather,
    var hourly:Hourly
)
data class CurrentWeather (
    var temperature:Float,
    var windspeed:Float,
    var winddirection:Int,
    var weathercode:Int,
    var time:String
)
data class Hourly (
    var time:ArrayList<String> ,
    var temperature_2m:ArrayList<Float>,
    var weathercode:ArrayList<Int>,
    var pressure_msl:ArrayList<Double>
)