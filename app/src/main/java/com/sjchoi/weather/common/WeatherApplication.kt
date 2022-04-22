package com.sjchoi.weather.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.telecom.ConnectionService
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import java.util.jar.Manifest

class WeatherApplication  : Application() {
    companion object{
        private lateinit var application: WeatherApplication
        fun getWeatherApplication() = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        setAllActivitySettings()
    }

    /**
     * 모든 액티비티를 수직하면 세팅
     * 모든 화면에서 공통적으로 작동이 필요할때만 사용(함부로 사용 x)
     */
    private fun setAllActivitySettings() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    fun isNetworkCheck() : Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val nw = cm.activeNetwork ?: return false
            val networkCapabilities = cm.getNetworkCapabilities(nw) ?: return false
            return when{
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ->true
                else -> false
            }
        }else{
            @Suppress("DEPRECATION")
            return cm.activeNetworkInfo?.isConnected ?: false
        }
    }

    fun toastMessage(text:String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}