package com.example.myprojectapplication.screens.home

import HourlyForecast
import android.annotation.SuppressLint
import android.location.Location
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapplication.app.MyApp
import com.example.myprojectapplication.model.CurrentWeather
import com.example.myprojectapplication.services.WeatherRestClient
import com.example.myprojectapplication.services.location.LocationClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class HomeViewModel(val fragment: Fragment): ViewModel() {

    private var location = LocationClient(fragment)

    private val  _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather>
        get() = _currentWeather

    private val _hourlyForecast: MutableLiveData<HourlyForecast> = MutableLiveData()
    val hourlyForecast: LiveData<HourlyForecast>
        get() = _hourlyForecast

    private val application = (fragment.activity?.application as MyApp)
    private var _locationEvent : MutableLiveData<Location> = application.appLocation
    val locationEvent: LiveData<Location>
        get() = _locationEvent
    init {

    }
    @SuppressLint("SimpleDateFormat")
    fun getCurWeather() {
        viewModelScope.launch {
            val curWeatherResp = WeatherRestClient.getInstance()
                                    .currentWeatherApi
                                    .curWeather(_locationEvent.value?.latitude ?: application.defaultLat,
                                        _locationEvent.value?.longitude ?: application.defaultLon)
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
            location?.let {
                _locationEvent.postValue(it)
            }
        }
    }

    fun setLocationClient(fragment: Fragment) {
        location = LocationClient(fragment)
    }


}