package com.example.myprojectapplication.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CurrentWeatherRestClient {
    private var _api: CurrentWeatherAPI = createCurrentWeatherDBService()

    val api: CurrentWeatherAPI
        get() = _api

    companion object{


        private var mInstance: CurrentWeatherRestClient? = null



        private fun createCurrentWeatherDBService(): CurrentWeatherAPI {
            //create okhttp
            val httpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).build()

            //create retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            return retrofit.create(CurrentWeatherAPI::class.java)
        }

        fun getInstance() = mInstance ?: synchronized(this){
            mInstance ?: CurrentWeatherRestClient().also { mInstance = it }
        }
    }
}