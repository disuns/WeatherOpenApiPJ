package com.sjchoi.weather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.sjchoi.weather.adapter.weatherTabAdapter
import com.sjchoi.weather.common.GpsManager
import com.sjchoi.weather.common.GpsManager.convertLatLon
import com.sjchoi.weather.common.GpsManager.getLocation
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.databinding.ActivityMainBinding
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.fragment.TabFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tabItem = listOf("실시간 예보", "생활지수")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocation(this)
        Log.e("", GpsManager.getlat().toString()+"    "+ GpsManager.getlon())
        with(binding) {
            with(applicationTabAdapter()) {
                //weatherViewPager.setPageTransformer(FlipPagerTransformer())
                weatherViewPager.adapter = this
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