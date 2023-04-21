package com.example.myprojectapplication.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.example.myprojectapplication.R
import com.example.myprojectapplication.databinding.FragmentDailyItemBinding
import com.example.myprojectapplication.model.DailyForecastListElement
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [DailyItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DailyItemFragment : Fragment() {
    lateinit var binding: FragmentDailyItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyItemBinding.inflate(inflater, container, false)

        parentFragmentManager.setFragmentResultListener("dataDaily", this, object: FragmentResultListener{
            @SuppressLint("SimpleDateFormat")
            override fun onFragmentResult(requestKey: String, result: Bundle) {
                val element = result.getParcelable("dataDaily", DailyForecastListElement::class.java)
                binding.dailyElement = element

                binding.textViewDailyItemDate.text = SimpleDateFormat("E, MMM dd").format(Date((element?.dt
                    ?: 0) * 1000))

                binding.textViewDailyItemSunrise.text = SimpleDateFormat("HH:mma").format(Date((element?.sunrise
                    ?: 0) * 1000))
                binding.textViewDailyItemSunset.text = SimpleDateFormat("HH:mma").format(Date((element?.sunset
                    ?: 0) * 1000))

                val uri = "https://openweathermap.org/img/wn/${element?.weather?.get(0)?.icon}@2x.png"
                Glide.with(binding.root).load(uri).centerCrop().into(binding.imageViewDailyItemWeatherIcon)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        if (navBar != null) {
            navBar.visibility = View.GONE
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DailyItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DailyItemFragment().apply {
            }
    }
}