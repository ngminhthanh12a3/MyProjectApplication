package com.example.myprojectapplication.screens.home

import HourlyForecast
import android.graphics.Color
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapplication.model.CurrentWeather
import com.example.myprojectapplication.services.WeatherRestClient
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.watasolutions.week8_permission.services.location.LocationClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class HomeViewModel(val location: LocationClient): ViewModel() {


    private val  _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather>
        get() = _currentWeather

    private val _hourlyForecast: MutableLiveData<HourlyForecast> = MutableLiveData()
    val hourlyForecast: LiveData<HourlyForecast>
        get() = _hourlyForecast

    private var _locationEvent : MutableLiveData<Location> = MutableLiveData<Location>()
    val locationEvent: LiveData<Location>
        get() = _locationEvent

    fun getCurWeather() {
        viewModelScope.launch {
            val curWeatherResp = WeatherRestClient.getInstance()
                                    .currentWeatherApi
                                    .curWeather(_locationEvent.value?.latitude ?: 10.750,
                                        _locationEvent.value?.longitude ?: 106.667)
            curWeatherResp.dateFormatted = SimpleDateFormat("MMM d, HH:mm a")
                                            .format(Date(curWeatherResp.dt * 1000))
            _currentWeather.postValue(curWeatherResp)
        }
    }

    fun getHourlyForecast() {
        viewModelScope.launch {
            val hourlyForecast = WeatherRestClient.getInstance()
                                    .hourlyForecastApi
                                    .hourlyForecast(_locationEvent.value?.latitude ?: 10.750,
                                        _locationEvent.value?.longitude ?: 106.667)

            _hourlyForecast.postValue(hourlyForecast)
        }

    }

    fun getLocation() {
        viewModelScope.launch {
            val location = location.getLastLocation()
            Log.i("location", location.toString())
            location?.let {
                _locationEvent.postValue(it)
            }
        }
    }


}