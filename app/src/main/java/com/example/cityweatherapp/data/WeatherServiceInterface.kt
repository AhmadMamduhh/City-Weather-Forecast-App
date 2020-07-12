package com.example.cityweatherapp.data

import com.example.cityweatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherServiceInterface {
    @GET("/data/2.5/weather?q={cityName}&appid={apiKey}")
    fun getWeatherResponse(@Path("cityName") cityName: String,
                           @Path("apiKey") apiKey: String) : Call<WeatherResponse>
}