package com.example.myprojectapplication.screens.daily

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectapplication.R
import com.example.myprojectapplication.app.ViewModelFactory
import com.example.myprojectapplication.databinding.FragmentDailyBinding
import com.example.myprojectapplication.model.DailyForecastListElement
import com.google.android.material.bottomnavigation.BottomNavigationView

interface OnDailyItemClick{
    fun onCLickItem(element: DailyForecastListElement)
}

/**
 * A simple [Fragment] subclass.
 * Use the [DailyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DailyFragment : Fragment() {
    private lateinit var binding: FragmentDailyBinding
    lateinit var adapter: DailyAdapter
    lateinit var viewModel: DailyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DailyViewModel::class.java]
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpViewModelObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDailyForecast()
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        if (navBar != null) {
            navBar.visibility = View.VISIBLE
        }
    }
    private fun setUpViewModelObservers() {
        viewModel.newList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

    private fun setUpRecyclerView() {
        binding.rvDailyForecast.layoutManager = LinearLayoutManager(activity)
        adapter = DailyAdapter(onDailyItemClick)
        binding.rvDailyForecast.adapter = adapter
    }

    private val onDailyItemClick = object : OnDailyItemClick{
        override fun onCLickItem(element: DailyForecastListElement) {
            val result = Bundle()
            result.putParcelable("dataDaily", element)
            parentFragmentManager.setFragmentResult("dataDaily", result)
            val controller = findNavController()
            controller.navigate(R.id.action_navigation_daily_to_dailyItemFragment)
        }

    }
}