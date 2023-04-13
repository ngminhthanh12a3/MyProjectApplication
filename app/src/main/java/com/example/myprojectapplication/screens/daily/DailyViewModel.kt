package com.example.myprojectapplication.screens.daily

import android.location.Location
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapplication.app.MyApp
import com.example.myprojectapplication.model.DailyForecastListElement
import com.example.myprojectapplication.services.WeatherRestClient
import kotlinx.coroutines.launch

class DailyViewModel(private val fragment: Fragment): ViewModel() {

    private val application = (fragment.activity?.application as MyApp)
    private var _locationEvent : MutableLiveData<Location> = application.appLocation

    private val _newList: MutableLiveData<List<DailyForecastListElement>> = MutableLiveData()
    val newList: LiveData<List<DailyForecastListElement>>
        get() = _newList

    fun getDailyForecast() {
        viewModelScope.launch {
            Log.i("_locationEvent.value", _locationEvent.value.toString())
            val dailyForecast = WeatherRestClient.getInstance().currentWeatherApi.dailyForecast(
                _locationEvent.value?.latitude ?: application.defaultLat,
                _locationEvent.value?.longitude ?: application.defaultLon
                    )

            if (dailyForecast != null) {
                Log.i("dailyForecast", dailyForecast.list.toString())
            }
            if (dailyForecast != null) {
                _newList.postValue(dailyForecast.list)
            }
        }
    }
}