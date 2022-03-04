package com.sjchoi.weather.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle

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
}