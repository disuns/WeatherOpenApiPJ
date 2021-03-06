package com.sjchoi.weather.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WeatherTabAdapter(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity){
    private val fragmentArray = arrayListOf<Fragment>()

    fun addFragment(fragment: Fragment) = fragmentArray.add(fragment)


    override fun createFragment(position: Int) = fragmentArray[position]


    override fun getItemCount() = fragmentArray.size

}