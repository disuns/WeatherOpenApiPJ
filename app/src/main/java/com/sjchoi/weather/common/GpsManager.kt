package com.sjchoi.weather.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import android.Manifest

@SuppressLint("StaticFieldLeak")
object GpsManager {

    private lateinit var mFusedlocationClient: FusedLocationProviderClient
    private lateinit var mCurrentLocation: Location
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var ornerActivity : Activity
    private var lat : Double = 0.0
    private var lon : Double = 0.0
    private var xLat : Double = 0.0
    private var yLon : Double = 0.0
    private val handler = Handler(Looper.getMainLooper())
    private var provider : String = ""

    fun getlat():Int{
        return lat.toInt()
    }
    fun getlon():Int{
        return lon.toInt()
    }
    fun getxLat():Int{
        return xLat.toInt()
    }
    fun getyLon():Int{
        return yLon.toInt()
    }

    fun getLocation(activity : Activity) {
        ornerActivity = activity
        if (WeatherApplication.getWeatherApplication().isNetworkCheck()) {
            handler.postDelayed({
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
                    else -> {}
                }
                gpsCheckPermissionLocation()
            }, 500L)
        }
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

    fun gpsCheckPermissionLocation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            permissionCheck()
        }else{
            initMapLocation()
        }
    }
    @SuppressLint("MissingPermission")
    private fun initMapLocation() {
        mLocationRequest = LocationRequest.create().apply {
            priority = if(provider.equals(LocationManager.GPS_PROVIDER, ignoreCase = true)){
                LocationRequest.PRIORITY_HIGH_ACCURACY
            }else{
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            }
        }
        val builder = LocationSettingsRequest.Builder().apply {
            addLocationRequest(mLocationRequest)
        }

        val mSettingsClient = LocationServices.getSettingsClient(ornerActivity)

        val mLocationSettingRequest = builder.build()
        val locationResponse = mSettingsClient.checkLocationSettings(mLocationSettingRequest)

        mFusedlocationClient = LocationServices.getFusedLocationProviderClient(ornerActivity)

        with(locationResponse){
            addOnSuccessListener {
                mFusedlocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
                Log.e("","위치받기 성공")
            }
            addOnFailureListener{e->
                val exception = e as ApiException
                Log.e("","위치받기 실패 : $exception")
            }
        }

    }

    private fun permissionCheck(){
        TedPermission.create().setPermissionListener(permissionListener).setRationaleMessage("위치제공이 필요합니다.").setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).check()
    }

    private val permissionListener = object : PermissionListener{
        override fun onPermissionGranted() {
            initMapLocation()
        }
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            WeatherApplication.getWeatherApplication().toastMessage("위치제공이 필요합니다.")
        }
    }

    private val mLocationCallback : LocationCallback =object :LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            mCurrentLocation = locationResult.locations[0]
            lat = mCurrentLocation.latitude
            lon = mCurrentLocation.longitude
            convertGRID_GPS(0)
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }
    }

    private fun convertGRID_GPS(mode: Int) {
        val RE = 6371.00877 // 지구 반경(km)
        val GRID = 5.0 // 격자 간격(km)
        val SLAT1 = 30.0 // 투영 위도1(degree)
        val SLAT2 = 60.0 // 투영 위도2(degree)
        val OLON = 126.0 // 기준점 경도(degree)
        val OLAT = 38.0 // 기준점 위도(degree)
        val XO = 43.0 // 기준점 X좌표(GRID)
        val YO = 136.0 // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //
        val DEGRAD = Math.PI / 180.0
        val RADDEG = 180.0 / Math.PI
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD
        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn
        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / Math.pow(ro, sn)
        if (mode == 0) {
            var ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5)
            ra = re * sf / Math.pow(ra, sn)
            var theta = lon * DEGRAD - olon
            if (theta > Math.PI) theta -= 2.0 * Math.PI
            if (theta < -Math.PI) theta += 2.0 * Math.PI
            theta *= sn
            xLat = Math.floor(ra * Math.sin(theta) + XO + 0.5)
            yLon = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5)
        } else {
            val xn = lat - XO
            val yn = ro - lon + YO
            var ra = Math.sqrt(xn * xn + yn * yn)
            if (sn < 0.0) {
                ra = -ra
            }
            var alat = Math.pow(re * sf / ra, 1.0 / sn)
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5
            var theta = 0.0
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0
            } else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5
                    if (xn < 0.0) {
                        theta = -theta
                    }
                } else theta = Math.atan2(xn, yn)
            }
            val alon = theta / sn + olon
            lat = alat * RADDEG
            lon = alon * RADDEG
        }
    }

}