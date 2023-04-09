package com.example.myprojectapplication.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRestClient {
    private var _currentWeatherApi: WeatherAPI = createCurrentWeatherDBService()

    val currentWeatherApi: WeatherAPI
        get() = _currentWeatherApi

    private var _hourlyForecastApi: WeatherAPI = createHourlyForecastDBService()
    val hourlyForecastApi: WeatherAPI
        get() = _hourlyForecastApi

    companion object{

        private var mInstance: WeatherRestClient? = null

        private fun createCurrentWeatherDBService(): WeatherAPI {
            //create okhttp
            val httpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).build()

            //create retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            return retrofit.create(WeatherAPI::class.java)
        }
        private fun createHourlyForecastDBService(): WeatherAPI {
            //create okhttp
            val httpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).build()

            //create retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://pro.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            return retrofit.create(WeatherAPI::class.java)
        }
        fun getInstance() = mInstance ?: synchronized(this){
            mInstance ?: WeatherRestClient().also { mInstance = it }
        }
    }
}