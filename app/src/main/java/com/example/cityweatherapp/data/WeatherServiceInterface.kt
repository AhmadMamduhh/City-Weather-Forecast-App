package com.example.cityweatherapp.data

import com.example.cityweatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherServiceInterface {
    @GET("/data/2.5/weather")
    fun getWeatherResponse(@Query("q") cityName: String,
                           @Query("appid") apiKey: String) : Call<WeatherResponse>
}