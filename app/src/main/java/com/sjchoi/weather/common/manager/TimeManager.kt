package com.sjchoi.weather.common.manager

import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
    private var instance: TimeManager? = null
    private lateinit var date: Date

    fun getTimeManager (): TimeManager {
        if (instance == null) {
            instance = this
        }

        return instance as TimeManager
    }

    fun urlNowDate() : String{
        var now = System.currentTimeMillis()
        date = Date(now)
        val dateFormat = SimpleDateFormat("yyyyMMdd")

        val timeFormat = SimpleDateFormat("HH")

        if(timeFormat.format(date)<"01") {
            now-=7200000
            date = Date(now)
        }
        return dateFormat.format(date)
    }

    fun urlNowTime() : String{
        var now = System.currentTimeMillis()
        now-=1800000L
        date = Date(now)
        val timeFormat = SimpleDateFormat("HHmm")
        val timeFormat2 = SimpleDateFormat("HH")

        return if(timeFormat2.format(date)<"01") {
            "2330"
        }else {
            timeFormat.format(date)
        }
    }

    fun urlTimeFcstDate() : String{
        var now = System.currentTimeMillis()
        date = Date(now)
        val timeFormat = SimpleDateFormat("HH")
        val dateFormat = SimpleDateFormat("yyyyMMdd")

        if(timeFormat.format(date)<"02") {
            now-=86400000
            date = Date(now)
        }

        return dateFormat.format(date)
    }
    fun urlTimeFcstTime() : String{
        var now = System.currentTimeMillis()
        date = Date(now)
        val timeFormat = SimpleDateFormat("HH")

        return if(timeFormat.format(date)>"23" || timeFormat.format(date)<"02")
            "2300"
        else if (timeFormat.format(date)>"20")
            "2000"
        else if (timeFormat.format(date)>"17")
            "1700"
        else if (timeFormat.format(date)>"14")
            "1400"
        else if (timeFormat.format(date)>"11")
            "1100"
        else if (timeFormat.format(date)>"08")
            "0800"
        else if (timeFormat.format(date)>"05")
            "0500"
        else
            "0200"
    }
}