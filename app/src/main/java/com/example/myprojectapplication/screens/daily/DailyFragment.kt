package com.example.myprojectapplication.screens.daily

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.myprojectapplication.R
import com.example.myprojectapplication.app.ViewModelFactory
import com.example.myprojectapplication.databinding.FragmentDailyBinding

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
    }
    private fun setUpViewModelObservers() {
        viewModel.newList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

    private fun setUpRecyclerView() {
        binding.rvDailyForecast.layoutManager = LinearLayoutManager(activity)
        adapter = DailyAdapter()
        binding.rvDailyForecast.adapter = adapter
    }
}