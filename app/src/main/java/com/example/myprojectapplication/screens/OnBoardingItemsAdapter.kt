package com.example.myprojectapplication.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myprojectapplication.databinding.OnboardingItemContainerBinding

class OnBoardingItemsAdapter(private val onBoardingItems: List<OnBoardingItem>): RecyclerView.Adapter<OnBoardingItemsAdapter.OnBoardingItemViewHolder>() {
    inner class OnBoardingItemViewHolder(private val binding: OnboardingItemContainerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(onBoardingItem: OnBoardingItem)
        {
            binding.textViewOnBoardingTitle.text = onBoardingItem.title
            Glide.with(binding.root.context).load(onBoardingItem.imageLink).into(binding.imageViewOnBoardingImage)
            binding.textViewOnBoardingSubTitle.text = onBoardingItem.subTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OnboardingItemContainerBinding.inflate(inflater, parent, false)
        return OnBoardingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }
}