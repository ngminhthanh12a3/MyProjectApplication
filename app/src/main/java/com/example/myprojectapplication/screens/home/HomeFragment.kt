package com.example.myprojectapplication.screens.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myprojectapplication.R
import com.example.myprojectapplication.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel =ViewModelProvider(this)[HomeViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpVMObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCurWeather()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpVMObservers() {
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + it.weather[0].icon + "@2x.png")
                .into(binding.imageViewWeatherIcon)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                }
    }
}