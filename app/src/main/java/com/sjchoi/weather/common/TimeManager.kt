package com.sjchoi.weather.common

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
    private var instance: TimeManager? = null
    private lateinit var date: Date

    fun getTimeManager (): TimeManager  {
        if (TimeManager.instance == null) {
            TimeManager.instance = this
        }

        return TimeManager.instance as TimeManager
    }

    private fun currentTimeDate(){
        val now = System.currentTimeMillis()
        date = Date(now)
    }

    fun urlNowDate() : String{
        currentTimeDate()
        val dateFormat = SimpleDateFormat("yyyyMMdd")

        return dateFormat.format(date)
    }

    fun urlNowTime() : String{
        currentTimeDate()
        val timeFormat = SimpleDateFormat("HHmm")
        return timeFormat.format(date)
    }

    fun urlFcstTime() : String{
        var now = System.currentTimeMillis()
        now-=7200000L
        date = Date(now)
        val timeFormat = SimpleDateFormat("HH00")
        return timeFormat.format(date)
    }
}