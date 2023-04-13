package com.example.myprojectapplication.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.myprojectapplication.databinding.FragmentOnBoardingBinding
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.myprojectapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [OnBoardingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnBoardingFragment : Fragment() {
    lateinit var binding: FragmentOnBoardingBinding
    private lateinit var onBoardingItemsAdapter: OnBoardingItemsAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        setOnboardingItems()
        setupIndicators()
        // Inflate the layout for this fragment
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        if (navBar != null) {
            navBar.visibility = View.GONE
        }
        return binding.root
    }

    private fun setupIndicators() {
        indicatorContainer = binding.indicatorsContainer
        val indicators = arrayOfNulls<ImageView>(onBoardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices)
        {
            indicators[i] = ImageView(binding.root.context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(binding.root.context,
                                                R.drawable.indicator_inactive_background)
                )
                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }

    private fun setOnboardingItems() {
        val textViewTitleArray = arrayOf("Current Weather", "Weather Forecasts")
        val imageLinkArray = arrayOf("https://cdn-icons-png.flaticon.com/512/3820/3820152.png",
                                    "https://cdn-icons-png.flaticon.com/512/1779/1779780.png")
        val textViewSubTitleArray = arrayOf("Access current weather data for any location on Earth including over 200,000 cities!",
                                    "Weather forecasts, now casts and history in a fast and elegant way")
        onBoardingItemsAdapter = OnBoardingItemsAdapter(listOf(
            OnBoardingItem(imageLinkArray[0], textViewTitleArray[0], textViewSubTitleArray[0]),
            OnBoardingItem(imageLinkArray[1], textViewTitleArray[1], textViewSubTitleArray[1])))

        val onBoardingViewPager =binding.viewPager2OnBoarding

        //
        var index = 0
        var previousState = ViewPager2.SCROLL_STATE_IDLE

        onBoardingViewPager.adapter = onBoardingItemsAdapter
        onBoardingViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                index = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if ((index >= onBoardingItemsAdapter.itemCount - 1)
                    && previousState == ViewPager2.SCROLL_STATE_DRAGGING
                    && state == ViewPager2.SCROLL_STATE_IDLE
                )
                    navigateToHomeFragment()

                previousState = state
            }
        })

        binding.textViewSkip.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        val controller = findNavController()
        controller.navigate(R.id.action_onBoardingFragment_to_homeFragment)
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount)
        {
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i == position)
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.indicator_active_background))

            else
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.indicator_inactive_background))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OnBoardingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            OnBoardingFragment().apply {

            }
    }
}