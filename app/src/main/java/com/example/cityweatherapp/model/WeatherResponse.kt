package com.example.cityweatherapp.model

data class WeatherResponse(var weather: List<Weather>, var main: Temperature)
data class Weather(var main : String)
data class Temperature(var temp : Double)