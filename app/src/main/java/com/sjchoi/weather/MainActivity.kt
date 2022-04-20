package com.sjchoi.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.sjchoi.weather.adapter.weatherTabAdapter
import com.sjchoi.weather.databinding.ActivityMainBinding
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.fragment.TabFragment
import com.sjchoi.weather.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tabItem = listOf("실시간 예보", "생활지수")

    private lateinit var viewModel :WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        viewModel.getLocation(this@MainActivity)

        with(binding) {
            with(applicationTabAdapter()) {
                //weatherViewPager.setPageTransformer(FlipPagerTransformer())
                weatherViewPager.adapter = this
                weatherViewPager.isUserInputEnabled = false
            }

            TabLayoutMediator(weatherTab, weatherViewPager) { tab, position ->
                tab.text = tabItem[position]
            }.attach()
        }
    }

    private fun applicationTabAdapter(): weatherTabAdapter {
        val tabAdapter = weatherTabAdapter(this)
        with(tabAdapter) {
            addFragment(TabFragment.newInstance(WeatherTabEnum.Fcst))
            addFragment(TabFragment.newInstance(WeatherTabEnum.LifeIndex))
        }
        return tabAdapter
    }


}