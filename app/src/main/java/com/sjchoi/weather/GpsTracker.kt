package com.sjchoi.weather

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.sjchoi.weather.common.WeatherApplication

class GpsTracker {

    fun findGPS(){
//        if(ActivityCompat.checkSelfPermission(WeatherApplication.getWeatherApplication().applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(WeatherApplication.getWeatherApplication()))
//        }else{
//
//        }
        var lastLocation : Location = locationManager.getLastKnowLocation(LocationManager.GPS_PROVIDER)
    }
}