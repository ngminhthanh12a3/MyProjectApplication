package com.example.myprojectapplication.services

import HourlyForecast
import com.example.myprojectapplication.model.CurrentWeather
import com.example.myprojectapplication.model.DailyForecast
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

    @GET("forecast/daily")
    suspend fun dailyForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("cnt") cnt: Int = 16
    ) : DailyForecast
}