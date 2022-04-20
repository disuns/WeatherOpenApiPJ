package com.sjchoi.weather

import android.Manifest
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val handler = Handler(Looper.getMainLooper())
    private var provider : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Loading()
    }

    private fun Loading(){

        if (WeatherApplication.getWeatherApplication().isNetworkCheck()) {
            handler.postDelayed({
                gpsPermission()
                gpsCheckPermissionLocation()
            }, 1000L)
        }else{
            WeatherApplication.getWeatherApplication().toastMessage("네트워크를 연결해주세요")
            finish()
        }
    }

    fun gpsPermission() {
        val mListenr = WeatherApplication.getWeatherApplication()
            .getSystemService(LOCATION_SERVICE) as LocationManager

        val isGPSLocation = mListenr.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkLocation = mListenr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        when {
            isGPSLocation -> {
                provider = LocationManager.GPS_PROVIDER
            }
            isNetworkLocation -> {
                provider = LocationManager.NETWORK_PROVIDER
            }
            else -> {""}
        }
    }

    fun gpsCheckPermissionLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            permissionCheck()
        }else{
            activityMainStart()
        }
    }

    private fun permissionCheck(){
        TedPermission.create().setPermissionListener(permissionListener).setRationaleMessage("위치제공이 필요합니다.").setPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).check()
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            activityMainStart()
        }
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            WeatherApplication.getWeatherApplication().toastMessage("위치제공이 필요합니다.")
            finish()
        }
    }

    private fun activityMainStart(){
        with(Intent(applicationContext, MainActivity::class.java)) {
            putExtra("provider", provider)
            startActivity(this)
        }
        finish()
    }
}