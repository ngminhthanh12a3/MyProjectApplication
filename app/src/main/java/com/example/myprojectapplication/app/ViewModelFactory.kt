package com.example.myprojectapplication.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myprojectapplication.screens.home.HomeFragment
import com.example.myprojectapplication.screens.home.HomeViewModel

class ViewModelFactory(val fragment: HomeFragment) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(fragment) as T
        }
        throw IllegalArgumentException("unknown view model")
    }
}