package com.sjchoi.weather.common

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

object GpsManager {

    private lateinit var mFusedlocationClient: FusedLocationProviderClient
    private var lat : Double = 0.0
    private var lon : Double = 0.0

    fun getLocation(activity : Activity) {
        if (WeatherApplication.getWeatherApplication().isNetworkCheck()) {
            if (ActivityCompat.checkSelfPermission(
                    WeatherApplication.getWeatherApplication().applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    WeatherApplication.getWeatherApplication().applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                var permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST)
            } else {
                mFusedlocationClient = LocationServices.getFusedLocationProviderClient(activity)
                mFusedlocationClient.lastLocation.addOnSuccessListener {
                    lat = it.latitude
                    lon = it.longitude
                }
            }
        }
    }
}