package com.example.myprojectapplication.screens.daily

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myprojectapplication.databinding.DailyItemBinding
import com.example.myprojectapplication.model.DailyForecastListElement
import java.text.SimpleDateFormat
import java.util.*

class DailyViewHolder(private val binding: DailyItemBinding): RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentDailyForeCastElement: DailyForecastListElement
    private lateinit var callback: OnDailyItemClick

    init {
        binding.root.setOnClickListener {
            callback.onCLickItem(currentDailyForeCastElement)
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun bindData(idol: DailyForecastListElement?, callback: OnDailyItemClick) {
        this.callback = callback
        if (idol != null) {
            currentDailyForeCastElement = idol
        }
        binding.dailyData = idol
        idol!!.dateFormatted = SimpleDateFormat("E, MMM d")
            .format(Date(idol.dt * 1000))
        val uri ="https://openweathermap.org/img/wn/${idol?.weather?.get(0)?.icon}@2x.png"
        Glide.with(binding.root.context).load(uri).into(binding.imageViewListWeatherIcon)
    }

    companion object{
        fun from(parent: ViewGroup) :DailyViewHolder
        {
            val binding = DailyItemBinding.inflate(LayoutInflater.from(parent.context),
                                                    parent, false)
            return DailyViewHolder(binding)
        }
    }
}