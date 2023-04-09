package com.example.myprojectapplication.app

import android.app.Application
import com.watasolutions.week8_permission.services.location.LocationClient

class MyApp : Application() {
    lateinit var locationClient: LocationClient
    override fun onCreate() {
        super.onCreate()
        locationClient = LocationClient(this)
    }
}