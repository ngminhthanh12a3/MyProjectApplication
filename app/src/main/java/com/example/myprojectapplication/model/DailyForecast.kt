package com.example.myprojectapplication.model

import com.google.gson.annotations.SerializedName

data class DailyForecast(val city: DailyForecastCity,
                         val cod: String,
                         val message: Double,
                         val cnt: Long,
                         val list: List<DailyForecastListElement>)

data class DailyForecastCity (
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long
)

data class DailyForecastListElement (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    val weather: List<Weather>,
    val speed: Double,
    val deg: Long,
    val gust: Double,
    val clouds: Long,
    val pop: Double,
    val rain: Double,
    var dateFormatted: String = ""
)

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)