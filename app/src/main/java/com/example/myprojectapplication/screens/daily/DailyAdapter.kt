package com.example.myprojectapplication.screens.daily

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myprojectapplication.model.DailyForecastListElement

class DailyAdapter(private val callback: OnDailyItemClick) : ListAdapter<DailyForecastListElement, DailyViewHolder>(DailyDiffUtil()) {
    class DailyDiffUtil:  DiffUtil.ItemCallback<DailyForecastListElement>() {
        override fun areItemsTheSame(oldItem: DailyForecastListElement, newItem: DailyForecastListElement): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: DailyForecastListElement, newItem: DailyForecastListElement): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val idol = getItem(position)
        holder.bindData(idol, callback)
    }

}
