package com.example.myprojectapplication.app

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myprojectapplication.screens.daily.DailyViewModel
import com.example.myprojectapplication.screens.home.HomeFragment
import com.example.myprojectapplication.screens.home.HomeViewModel

class ViewModelFactory(val fragment: Fragment) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(fragment) as T
        }
        if(modelClass.isAssignableFrom(DailyViewModel::class.java))
        {
            Log.i("DailyViewModel", "DailyViewModel")
            return DailyViewModel(fragment) as T
        }
        throw IllegalArgumentException("unknown view model")
    }
}