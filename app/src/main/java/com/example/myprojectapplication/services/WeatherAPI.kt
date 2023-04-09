package com.example.myprojectapplication.services

import HourlyForecast
import com.example.myprojectapplication.model.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun curWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double
    ) : CurrentWeather

    @GET("forecast/hourly")
    suspend fun hourlyForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double
    ) : HourlyForecast
}