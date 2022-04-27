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

        return with(timeFormat.format(date)){
            if(this>"23" || this<"02")
                "2300"
            else if (this>"20")
                "2000"
            else if (this>"17")
                "1700"
            else if (this>"14")
                "1400"
            else if (this>"11")
                "1100"
            else if (this>"08")
                "0800"
            else if (this>"05")
                "0500"
            else
                "0200"
        }
    }

    fun urlWeekFcstTime():String{
        var now = System.currentTimeMillis()
        date = Date(now)

        val timeFormat = SimpleDateFormat("HH")
        val dateFormat: SimpleDateFormat = with(timeFormat.format(date)) {
            if (this > "06" || this <= "18")
                SimpleDateFormat("yyyyMMdd0600")
            else
                SimpleDateFormat("yyyyMMdd1800")
        }

        return dateFormat.format(date)
    }
}