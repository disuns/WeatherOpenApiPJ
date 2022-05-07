package com.sjchoi.weather.activity

import android.Manifest
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.databinding.ActivitySplashBinding

class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val handler = Handler(Looper.getMainLooper())
    private var provider : String = ""

    private val weatherApplication = WeatherApplication.getWeatherApplication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading()
    }

    private fun loading(){

        if (weatherApplication.isNetworkCheck()) {
            handler.postDelayed({
                gpsPermission()
                gpsCheckPermissionLocation()
            }, 1000L)
        }else{
            weatherApplication.toastMessage("네트워크를 연결해주세요")
            finish()
        }
    }

    private fun gpsPermission() {
        val mListenr = weatherApplication.getSystemService(LOCATION_SERVICE) as LocationManager

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

    private fun gpsCheckPermissionLocation(){
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
            weatherApplication.toastMessage("위치제공이 필요합니다.")
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