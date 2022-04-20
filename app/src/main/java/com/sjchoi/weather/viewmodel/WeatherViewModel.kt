package com.sjchoi.weather.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.sjchoi.weather.common.*
import com.sjchoi.weather.common.manager.TimeManager
import com.sjchoi.weather.dataclass.FcstData
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.*

class WeatherViewModel() : ViewModel() {

    private val weatherService = RetrofitOkHttpManager.weatherRESTService

    private var nowFcstData : MutableLiveData<FcstData> = MutableLiveData()
    private var timeFcstData : MutableLiveData<FcstData> = MutableLiveData()

    private lateinit var mFusedlocationClient: FusedLocationProviderClient
    private lateinit var mCurrentLocation: Location
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var ornerActivity : Activity
    private var lat : Double = 0.0
    private var lon : Double = 0.0
    private var xLat : Double = 0.0
    private var yLon : Double = 0.0
    private var provider : String = ""

    private val handler = Handler(Looper.getMainLooper())

    fun checkNowFcstData() : Boolean {
        return nowFcstData.let {
            if(it.value!=null){
                if (it.value!!.response.header.resultCode != NO_ERROR) {
                    DataConvert.getDataConvert().dataPotalResultCode(it.value!!.response.header.resultCode)
                    false
                }else{ true }
            }else{
                false
            }
        }

    }

    fun getNowFcstData():MutableLiveData<FcstData> = nowFcstData

    fun nowFcstRest() {
        val nowFcstCall: Call<FcstData> = weatherService.requestNowFcst(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE,
            TimeManager.getTimeManager().urlNowDate(),
            TimeManager.getTimeManager().urlNowTime(),
            xLat.toInt().toString(),
            yLon.toInt().toString()
        )


        Log.e("",nowFcstCall.request().url.toString())

        nowFcstCall.enqueue(object : Callback<FcstData> {
            override fun onResponse(call: Call<FcstData>, response: Response<FcstData>) {
                if (response.isSuccessful) {
                    nowFcstData.value = response.body() as FcstData
                }
            }

            override fun onFailure(call: Call<FcstData>, t: Throwable) {
                WeatherApplication.getWeatherApplication().toastMessage(t.message.toString())
                Log.e("",t.message.toString())
            }
        })
    }

    fun checkTimeFcstData() : Boolean {
        //if(timeFcstCheck){
       return timeFcstData.let {
            if (it.value != null) {
                if (it.value!!.response.header.resultCode != NO_ERROR) {
                    DataConvert.getDataConvert().dataPotalResultCode(it.value!!.response.header.resultCode)
                    false
                }else{ true }
            }
            else
                false
        }
    }

    fun getTimeFcstData():MutableLiveData<FcstData> = timeFcstData

    fun timeFcstRest(){
        val timeFcstCall: Call<FcstData> = weatherService.requestFcst(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE,
            TimeManager.getTimeManager().urlTimeFcstDate(),
            TimeManager.getTimeManager().urlTimeFcstTime(),
            xLat.toInt().toString(),
            yLon.toInt().toString()
        )

        timeFcstCall.enqueue(object :Callback<FcstData>{
            override fun onResponse(call: Call<FcstData>, response: Response<FcstData>) {
                if (response.isSuccessful) {
                    timeFcstData.postValue(response.body() as FcstData)
                }
            }

            override fun onFailure(call: Call<FcstData>, t: Throwable) {
                WeatherApplication.getWeatherApplication().toastMessage(t.message.toString())
                Log.e("",t.message.toString())
            }
        })
    }

    fun getLocation(activity : Activity) {
        ornerActivity = activity
        provider = ornerActivity.intent.getStringExtra("provider")!!
        initMapLocation()
//        if (ActivityCompat.checkSelfPermission(
//                WeatherApplication.getWeatherApplication().applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//            || ActivityCompat.checkSelfPermission(
//                WeatherApplication.getWeatherApplication().applicationContext,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            var permissions = arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//            ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST)
//        } else {
//            mFusedlocationClient = LocationServices.getFusedLocationProviderClient(activity)
//            mFusedlocationClient.lastLocation.addOnSuccessListener {
//                lat = it.latitude
//                lon = it.longitude
//                convertGRID_GPS(0)
//            }
//        }
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
            }
            addOnFailureListener{e->
                val exception = e as ApiException
                Log.e("","위치받기 실패 : $exception")
            }
        }

    }

    private val mLocationCallback : LocationCallback =object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            mCurrentLocation = locationResult.locations[0]
            lat = mCurrentLocation.latitude
            lon = mCurrentLocation.longitude
            convertGRID_GPS(0)
        }
        fun convertGRID_GPS(mode: Int) {
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
            var sn = tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
            sn = ln(cos(slat1) / cos(slat2)) / ln(sn)
            var sf = tan(Math.PI * 0.25 + slat1 * 0.5)
            sf = sf.pow(sn) * cos(slat1) / sn
            var ro = tan(Math.PI * 0.25 + olat * 0.5)
            ro = re * sf / ro.pow(sn)
            if (mode == 0) {
                var ra = tan(Math.PI * 0.25 + lat * DEGRAD * 0.5)
                ra = re * sf / ra.pow(sn)
                var theta = lon * DEGRAD - olon
                if (theta > Math.PI) theta -= 2.0 * Math.PI
                if (theta < -Math.PI) theta += 2.0 * Math.PI
                theta *= sn
                xLat = floor(ra * sin(theta) + XO + 0.5)
                yLon = floor(ro - ra * cos(theta) + YO + 0.5)
            } else {
                val xn = lat - XO
                val yn = ro - lon + YO
                var ra = sqrt(xn * xn + yn * yn)
                if (sn < 0.0) {
                    ra = -ra
                }
                var alat = (re * sf / ra).pow(1.0 / sn)
                alat = 2.0 * atan(alat) - Math.PI * 0.5
                var theta = 0.0
                if (abs(xn) <= 0.0) {
                    theta = 0.0
                } else {
                    if (abs(yn) <= 0.0) {
                        theta = Math.PI * 0.5
                        if (xn < 0.0) {
                            theta = -theta
                        }
                    } else theta = atan2(xn, yn)
                }
                val alon = theta / sn + olon
                lat = alat * RADDEG
                lon = alon * RADDEG
            }
            nowFcstRest()
            timeFcstRest()
        }
    }
}