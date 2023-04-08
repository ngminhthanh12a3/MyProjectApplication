package com.example.myprojectapplication.services

import com.example.myprojectapplication.model.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherAPI {
    @GET("weather")
    suspend fun curWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double
    ) : CurrentWeather
}