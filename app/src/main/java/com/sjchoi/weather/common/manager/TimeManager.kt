package com.sjchoi.weather.common.manager

import android.annotation.SuppressLint
import com.sjchoi.weather.R
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekDate
import java.text.SimpleDateFormat
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

    fun getFcstWeekUIDate(dayLater : Int) : WeekDate {
        val calendar = Calendar.getInstance()
        val now = System.currentTimeMillis()
        date = Date(now +(1000 * 60 * 60 * 24 * dayLater))
        val monthFormat = SimpleDateFormat("MM")
        val dayFormat = SimpleDateFormat("dd")

        calendar.time=date
        val strWeek:String= with(WeatherApplication.getWeatherApplication()){
            when(calendar.get(Calendar.DAY_OF_WEEK)){
                getString(R.string.NUM1).toInt()->{getString(R.string.sunday)}
                getString(R.string.NUM2).toInt()->{getString(R.string.monday)}
                getString(R.string.NUM3).toInt()->{getString(R.string.tuesday)}
                getString(R.string.NUM4).toInt()->{getString(R.string.wednesday)}
                getString(R.string.NUM5).toInt()->{getString(R.string.thursday)}
                getString(R.string.NUM6).toInt()->{getString(R.string.friday)}
                else->{getString(R.string.saturday)}
            }
        }

        return WeekDate(monthFormat.format(date),dayFormat.format(date),strWeek)

    }
}