package com.example.myprojectapplication.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapplication.model.CurrentWeather
import com.example.myprojectapplication.services.CurrentWeatherRestClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class HomeViewModel: ViewModel() {
    private val  _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather>
        get() = _currentWeather

    fun getCurWeather() {
        viewModelScope.launch {
            val curWeatherResp = CurrentWeatherRestClient.getInstance().api.curWeather(10.750, 106.667)
            curWeatherResp.dateFormatted = SimpleDateFormat("MMM d, HH:mm a")
                                            .format(Date(curWeatherResp.dt * 1000))
            _currentWeather.postValue(curWeatherResp)
        }
    }



}