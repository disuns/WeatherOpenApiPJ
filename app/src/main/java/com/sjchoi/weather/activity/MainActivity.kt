package com.sjchoi.weather.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.sjchoi.weather.R
import com.sjchoi.weather.WeatherViewModelFactory
import com.sjchoi.weather.adapter.WeatherTabAdapter
import com.sjchoi.weather.common.DataConvert.mapAddressConvert
import com.sjchoi.weather.common.PJRepository
import com.sjchoi.weather.databinding.ActivityMainBinding
import com.sjchoi.weather.fragment.IndexFragment
import com.sjchoi.weather.fragment.NaverMapFragment
import com.sjchoi.weather.fragment.FcstFragment
import com.sjchoi.weather.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tabItem = mutableListOf<String>()

    private lateinit var viewModel :WeatherViewModel

    private lateinit var mapFragment : NaverMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabItem = resources.getStringArray(R.array.Tab).toMutableList()

        val viewModelFactory = WeatherViewModelFactory(PJRepository())

        viewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
        if (savedInstanceState == null)
            viewModel.getLocation(this@MainActivity)

        with(binding) {
            viewModel.getAddress().observe(this@MainActivity){buttonMap.text = mapAddressConvert(it)}
            with(applicationTabAdapter()) {
                weatherViewPager.adapter = this
                weatherViewPager.isUserInputEnabled = false
            }

            TabLayoutMediator(weatherTab, weatherViewPager) { tab, position ->
                tab.text = tabItem[position]
            }.attach()

            buttonMap.setOnClickListener {
                naverMapFragmnet.visibility = View.VISIBLE
                backStackFragment(savedInstanceState)
            }
        }
    }

    private fun applicationTabAdapter(): WeatherTabAdapter {
        val tabAdapter = WeatherTabAdapter(this)
        with(tabAdapter) {
            addFragment(FcstFragment.newInstance())
            addFragment(IndexFragment.newInstance())
        }
        return tabAdapter
    }

    private fun backStackFragment(savedInstanceState: Bundle?) {
        mapFragment = NaverMapFragment.newInstance()

        with(supportFragmentManager.beginTransaction()){
            if (savedInstanceState == null)
                add(R.id.naverMapFragmnet,mapFragment).addToBackStack(null).commit()
            else
                replace(R.id.naverMapFragmnet,mapFragment).addToBackStack(null).commit()

        }
    }

}