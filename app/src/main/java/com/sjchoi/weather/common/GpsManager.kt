package com.sjchoi.weather.common

import android.Manifest
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

object GpsManager {

    private lateinit var mFusedlocationClient: FusedLocationProviderClient
    private var lat : Double = 0.0
    private var lon : Double = 0.0
    private var xLat : Double = 0.0
    private var yLon : Double = 0.0
    private val handler = Handler(Looper.getMainLooper())

    fun getlat():Double{
        return lat
    }
    fun getlon():Double{
        return lon
    }
    fun getxLat():Double{
        return xLat
    }
    fun getyLon():Double{
        return yLon
    }

    fun getLocation(activity : Activity) {
        if (WeatherApplication.getWeatherApplication().isNetworkCheck()) {
//            handler.postDelayed({
//                val mListenr = WeatherApplication.getWeatherApplication().getSystemService(LOCATION_SERVICE) as LocationManager
//
//                val isGPSLocation = mListenr.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                val isNetworkLocation = mListenr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//            })
//            if (ActivityCompat.checkSelfPermission(
//                    WeatherApplication.getWeatherApplication().applicationContext,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(
//                    WeatherApplication.getWeatherApplication().applicationContext,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                var permissions = arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//                ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST)
//            } else {
//                mFusedlocationClient = LocationServices.getFusedLocationProviderClient(activity)
//                mFusedlocationClient.lastLocation.addOnSuccessListener {
//                    lat = it.latitude
//                    lon = it.longitude
//                }
//            }
        }
    }

    fun convertLatLon(mode : Int ){
        val RE = 6371.00877
        val GRID = 5.0
        val SLAT1 =30.0
        val SLAT2 = 60.0
        val OLON = 126.0
        val OLAT = 38.0
        val XO = 43
        val YO = 136

        val DEGRAD = Math.PI / 180.0
        val RADDEG = 180.0 / Math.PI

        val re = RE/GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 * slat1 * 0.5)
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf,sn) * Math.cos(slat1)/sn
        var ro = Math.tan(Math.PI*0.25+olat*0.5)
        ro = re * sf / Math.pow(ro,sn)

        if(mode ==0 ){
            var ra = Math.tan(Math.PI*0.25+lat*DEGRAD*0.5)
            ra = re * sf/Math.pow(ra,sn)
            var theta = lon*DEGRAD - olon
            if(theta>Math.PI)theta-=2.0 * Math.PI
            if(theta<-Math.PI)theta+=2.0*Math.PI
            theta*=sn
            var x =Math.floor(ra*Math.sin(theta)+XO+0.5)
            var y = Math.floor(ro-ra*Math.cos(theta)+YO+0.5)
            xLat=x
            yLon=y
        }else{
            var xlat = xLat
            var ylon = yLon
            var xn =xlat-XO
            var yn = ro- ylon+YO
            var ra = Math.sqrt(xn*xn+yn*yn)
            if(sn<0.0)
                ra=-ra
            var alat = Math.pow((re*sf/ra), (1.0/sn))
            alat = 2.0*Math.atan(alat) - Math.PI*0.5
            var theta = 0.0
            if(Math.abs(xn)<=0.0)
                theta=0.0
            else{
                if(Math.abs(yn)<=0.0){
                    theta = Math.PI * 0.5
                    if(xn<0.0)
                        theta=-theta
                }
                else theta = Math.atan2(xn,yn)
            }
            var alon = theta/sn+olon
            xLat=alat*RADDEG
            yLon=alon*RADDEG
        }

    }
}