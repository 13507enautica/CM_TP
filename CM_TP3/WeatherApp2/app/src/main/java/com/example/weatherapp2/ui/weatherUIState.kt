package com.example.weatherapp2.ui


data class weatherUIState(

    val latitude:Float,
    val longitude:Float,
    val temperature:Float,
    val windspeed:Float,
    val winddirection:Int,
    val weathercode:Int,
    val seaLevelPressure:Float,
    val time:String

)
