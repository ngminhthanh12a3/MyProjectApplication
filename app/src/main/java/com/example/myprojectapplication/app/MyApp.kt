package com.example.myprojectapplication.app

import android.app.Application
import android.location.Location
import androidx.lifecycle.MutableLiveData

class MyApp: Application() {
    val defaultLon: Double = 106.667
    val defaultLat: Double = 10.750
    var appLocation: MutableLiveData<Location> = MutableLiveData()
}