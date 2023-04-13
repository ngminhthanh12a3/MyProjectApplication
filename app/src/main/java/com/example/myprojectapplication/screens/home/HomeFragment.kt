package com.example.myprojectapplication.screens.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myprojectapplication.R
import com.example.myprojectapplication.app.ViewModelFactory
import com.example.myprojectapplication.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watasolutions.week8_permission.services.location.LocationPermissionUtils
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), LocationPermissionUtils.LocationComponentListener {
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var lineChartHourlyForecast: LineChart

    var locationPermissionUtils: LocationPermissionUtils = LocationPermissionUtils()

    init {
        locationPermissionUtils.registerActivityResultForPermission(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[HomeViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        lineChartHourlyForecast = binding.lineChartHourlyForecast
        // Inflate the layout for this fragment
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        if (navBar != null) {
            navBar.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpVMObservers()
        binding.imageViewLocationIcon.setOnClickListener {
            locationPermissionUtils.requestLocationPermission(this)
        }
    }

    override fun onStart() {
        super.onStart()
        getHomeData()
        locationPermissionUtils.setListener(this)
        viewModel.setLocationClient(this)
    }

    private fun getHomeData() {
        viewModel.getCurWeather()
        viewModel.getHourlyForecast()
    }

    override fun onStop() {
        super.onStop()
        locationPermissionUtils.setListener(null)
    }


    @SuppressLint("SetTextI18n")
    private fun setUpVMObservers() {
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + it.weather[0].icon + "@2x.png")
                .into(binding.imageViewWeatherIcon)

        }

        viewModel.hourlyForecast.observe(viewLifecycleOwner) { it ->
            if(it.list.isNotEmpty())
            {
                val entries: MutableList<Entry> = mutableListOf()

                for(item in it.list)
                {
                    entries.add(Entry(it.list.indexOf(item).toFloat(), item.main.temp.toFloat()))
                }

                val dataSet = LineDataSet(entries, "Temperature")
                dataSet.setColors(Color.YELLOW)
                dataSet.setDrawValues(false)
                dataSet.setDrawCircles(false)

                val lineData = LineData(dataSet)

                lineChartHourlyForecast.data = lineData
                lineChartHourlyForecast.description.text = "Hourly Forecast"
                lineChartHourlyForecast.description.textSize = 14F
                lineChartHourlyForecast.xAxis.granularity = 1F

                val simpleDateFormat = SimpleDateFormat("HH a")
                val quarters = it.list.map { it1 ->
                    val dateString = simpleDateFormat.format(it1.dt * 1000)
                    dateString
                }

                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase): String {
                        return quarters[value.toInt()]
                    }
                }
                lineChartHourlyForecast.xAxis.valueFormatter = formatter
                lineChartHourlyForecast.xAxis.textColor = Color.WHITE
                lineChartHourlyForecast.axisLeft.textColor = Color.WHITE
                lineChartHourlyForecast.axisRight.textColor = Color.WHITE
                lineChartHourlyForecast.invalidate()

            }

        }

        viewModel.locationEvent.observe(viewLifecycleOwner) {
//            Log.e("TAG", "${it.longitude} -- ${it.latitude}")
            getHomeData()
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

    override fun onLocationFeatureEnable(isEnabled: Boolean) {
        if (isEnabled) {
            viewModel.getLocation()
        } else {
            Toast.makeText(context, "Permission is denied", Toast.LENGTH_SHORT).show()
        }
    }
}