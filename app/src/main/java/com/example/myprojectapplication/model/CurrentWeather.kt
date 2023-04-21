package com.example.myprojectapplication.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CurrentWeather(val coord: Coord,
                          val weather: List<Weather>,
                          val base: String,
                          val main: Main,
                          val visibility: Long,
                          val wind: Wind,
                          val clouds: Clouds,
                          var dt: Long,
                          val sys: Sys,
                          val timezone: Long,
                          val id: Long,
                          val name: String,
                          val cod: Long,
                        var dateFormatted: String)

data class Clouds (
    val all: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class Main (
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long
)

data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

@Parcelize
data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable

data class Wind (
    val speed: Double,
    val deg: Long
)