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

    fun urlNowDate() : String{
        var now = System.currentTimeMillis()
        date = Date(now)
        val dateFormat = SimpleDateFormat("yyyyMMdd")

        return dateFormat.format(date)
    }

    fun urlNowTime() : String{
        var now = System.currentTimeMillis()
        now-=3600000L
        date = Date(now)
        val timeFormat = SimpleDateFormat("HHmm")
        return timeFormat.format(date)
    }

    fun urlFcstTime() : String{
        var now = System.currentTimeMillis()
        now-=7200000L
        date = Date(now)
        val timeFormat = SimpleDateFormat("HH00")
        Log.e("",timeFormat.format(date))
        return timeFormat.format(date)
    }
}