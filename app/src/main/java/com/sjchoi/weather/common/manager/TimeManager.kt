package com.sjchoi.weather.common.manager

import android.annotation.SuppressLint
import com.sjchoi.weather.R
import com.sjchoi.weather.common.*
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekDate
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object TimeManager {
    private var instance: TimeManager? = null
    private lateinit var date: Date

    private val weatherApplication = WeatherApplication.getWeatherApplication()

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
            when{
                this>"20" ->"2000"
                this>"17"-> "1700"
                this>"14"->"1400"
                this>"11"->"1100"
                this>"08"->"0800"
                this>"05"->"0500"
                this>"02"->"0200"
                else->"2300"
            }
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
        val strWeek:String= with(weatherApplication){
            when(calendar.get(Calendar.DAY_OF_WEEK)){
                NUM1.toInt()->{getString(R.string.sunday)}
                NUM2.toInt()->{getString(R.string.monday)}
                NUM3.toInt()->{getString(R.string.tuesday)}
                NUM4.toInt()->{getString(R.string.wednesday)}
                NUM5.toInt()->{getString(R.string.thursday)}
                NUM6.toInt()->{getString(R.string.friday)}
                else->{getString(R.string.saturday)}
            }
        }

        return WeekDate(monthFormat.format(date),dayFormat.format(date),strWeek)
    }

    fun urlAirQualityDate():String{
        val now = System.currentTimeMillis()
        date = Date(now)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        return dateFormat.format(date)
    }
}