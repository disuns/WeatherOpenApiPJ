package com.sjchoi.weather

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.sjchoi.weather.adapter.weatherTabAdapter
import com.sjchoi.weather.databinding.ActivityMainBinding
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.fragment.NaverMapFragment
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

            buttonMap.setOnClickListener {
//                var mapFragment = supportFragmentManager.findFragmentById(R.id.navermapFragment) as MapFragment
//                if(mapFragment==null){
//                    mapFragment = MapFragment.newInstance()
//                    supportFragmentManager.beginTransaction().add(R.id.navermapFragment,mapFragment).commit()
//                }
//                mapFragment?.getMapAsync(this@MainActivity)

                naverMap.visibility = View.VISIBLE
                val mapFragment = NaverMapFragment.newInstance(viewModel.getLat(),viewModel.getLon())
                supportFragmentManager.beginTransaction().add(R.id.naverMap,mapFragment).commit()

            }
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