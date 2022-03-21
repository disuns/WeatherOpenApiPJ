package com.sjchoi.weather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sjchoi.weather.adapter.weatherTabAdapter
import com.sjchoi.weather.databinding.ActivityMainBinding
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.fragment.TabFragment
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tabItem = listOf("실시간 예보", "생활지수")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            with(applicationTabAdapter()){
                //weatherViewPager.setPageTransformer(FlipPagerTransformer())
                weatherViewPager.adapter = this
            }

            TabLayoutMediator(weatherTab, weatherViewPager) {
                tab, position -> tab.text = tabItem[position]
            }.attach()
        }
    }

    private fun applicationTabAdapter(): weatherTabAdapter {
        val tabAdapter = weatherTabAdapter(this)
        with(tabAdapter){
            addFragment(TabFragment.newInstance(WeatherTabEnum.Vilage))
            addFragment(TabFragment.newInstance(WeatherTabEnum.LifeIndex))
        }
        return tabAdapter
    }

    internal inner class FlipPagerTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            with(page){
                translationX = -position * width
                cameraDistance = 12000f
                when {
                    position < 0.5 && position > -0.5 -> {
                        View.VISIBLE
                    }
                    else -> {
                        View.INVISIBLE
                    }
                }.also { visibility = it }

                when {
                    position < -1 -> {     // [-Infinity,-1)
                        alpha = 0f
                    }
                    position <= 0 -> {    // [-1,0]
                        alpha = 1f
                        rotationY = 180 * (1 - abs(position) + 1)
                    }
                    position <= 1 -> {    // (0,1]
                        alpha = 1f
                        (-180 * (1 - abs(position) + 1)).also { rotationY = it }
                    }
                    else -> alpha = 0f
                }
            }
        }
    }
}