package com.sjchoi.weather.common.manager

import android.annotation.SuppressLint
import com.sjchoi.weather.dataclass.WeekDate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@SuppressLint("SimpleDateFormat")
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
        val now = System.currentTimeMillis()
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
        val now = System.currentTimeMillis()
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

    fun getFcstWeekUIDate(dayLater : Int) : WeekDate{
        val calendar = Calendar.getInstance()
        var now = System.currentTimeMillis()
        date = Date(now +(1000 * 60 * 60 * 24 * dayLater))
        val monthFormat = SimpleDateFormat("MM")
        val dayFormat = SimpleDateFormat("dd")

        calendar.time=date
        val strWeek:String= when(calendar.get(Calendar.DAY_OF_WEEK)){
            1->{"일"}
            2->{"월"}
            3->{"화"}
            4->{"수"}
            5->{"목"}
            6->{"금"}
            else->{"토"}
        }

        return WeekDate(monthFormat.format(date),dayFormat.format(date),strWeek)

    }
}