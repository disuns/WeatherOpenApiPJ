package com.sjchoi.weather.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.sjchoi.weather.common.*
import com.sjchoi.weather.common.manager.TimeManager.getTimeManager
import com.sjchoi.weather.common.manager.TimeManager.urlWeekFcstTime
import com.sjchoi.weather.dataclass.datapotal.fcstdata.FcstData
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekRainSkyData
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex
import com.sjchoi.weather.dataclass.reverseGeocoder.ReverseGeocoder
import kotlinx.coroutines.launch
import kotlin.math.*

class WeatherViewModel(private val repository: PJRepository) : ViewModel() {

    private val dataConvert = DataConvert.getDataConvert()
    private val weatherApplication = WeatherApplication.getWeatherApplication()

    private var nowFcstData : MutableLiveData<FcstData> = MutableLiveData()
    private var timeFcstData : MutableLiveData<FcstData> = MutableLiveData()
    private var weekRainSkyData : MutableLiveData<WeekRainSkyData> = MutableLiveData()
    private var airQualityData : MutableLiveData<AirQualityIndex> = MutableLiveData()
    private var rltmStationData : MutableLiveData<RltmStationIndex> = MutableLiveData()
    private var lat : MutableLiveData<Double> = MutableLiveData()
    private var lon : MutableLiveData<Double> = MutableLiveData()
    private var address : MutableLiveData<ReverseGeocoder> = MutableLiveData()

    private lateinit var mFusedlocationClient: FusedLocationProviderClient
    private lateinit var mCurrentLocation: Location
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var ornerActivity : Activity
    private lateinit var progressDialog : ProgressDialog
    private var xLat : Double = 0.0
    private var yLon : Double = 0.0
    private var provider : String = ""

    private fun getXLat() = xLat.toInt()
    private fun getYLon() = yLon.toInt()
    fun getNowFcstData() = nowFcstData
    fun getTimeFcstData() = timeFcstData
    fun getWeekRainSkyData() = weekRainSkyData
    fun getAirQualityIndex() = airQualityData
    fun getRltmStationIndex() = rltmStationData
    fun getLat() = lat
    fun getLon() = lon
    fun getAddress() = address

    fun checkWeekRainSkyData() : Boolean {
        return weekRainSkyData.value?.let {
            if (it.response.header.resultCode != NO_ERROR) {
                dataConvert.dataPotalResultCode(it.response.header.resultCode)
                false
            } else {
                true
            }
        } ?: false
    }

    fun checkNowFcstData() : Boolean {
         return nowFcstData.value?.let {
            if (it.response.header.resultCode != NO_ERROR) {
                dataConvert.dataPotalResultCode(it.response.header.resultCode)
                false
            } else {
                true
            }
        } ?: false
    }

    fun checkTimeFcstData() : Boolean {
        return timeFcstData.value?.let {
            if (it.response.header.resultCode != NO_ERROR) {
                dataConvert.dataPotalResultCode(it.response.header.resultCode)
                false
            } else {
                true
            }
        } ?: false
    }

    fun checkAirQualityData() : Boolean {
        return airQualityData.value?.let {
            if (it.response.header.resultCode != NO_ERROR) {
                dataConvert.dataPotalResultCode(it.response.header.resultCode)
                false
            } else {
                true
            }
        } ?: false
    }

    fun checkRltmStationData() : Boolean {
        return rltmStationData.value?.let {
            if (it.response.header.resultCode != NO_ERROR) {
                dataConvert.dataPotalResultCode(it.response.header.resultCode)
                false
            } else {
                true
            }
        } ?: false
    }

    fun getLocation(activity : Activity, progress: ProgressDialog) {
        ornerActivity = activity
        progressDialog = progress
        //live코드
//        provider = ornerActivity.intent.getStringExtra("provider")!!
//        initMapLocation()

        //가상머신용 코드
        lon.value =126.88237267230349
        lat.value =37.51982548626224
        convertGRIDGPS(0)
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
            lat.value =mCurrentLocation.latitude
            lon.value =mCurrentLocation.longitude
            restReGe(lat.value!!,lon.value!!)
            convertGRIDGPS(NUM0.toInt())
        }
    }

    fun convertGRIDGPS(mode: Int) {
        val conLat = lat.value!!
        val conLon = lon.value!!

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
        var sn = tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = ln(cos(slat1) / cos(slat2)) / ln(sn)
        var sf = tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = sf.pow(sn) * cos(slat1) / sn
        var ro = tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / ro.pow(sn)
        if (mode == 0) {
            var ra = tan(Math.PI * 0.25 + conLat * DEGRAD * 0.5)
            ra = re * sf / ra.pow(sn)
            var theta = conLon * DEGRAD - olon
            if (theta > Math.PI) theta -= 2.0 * Math.PI
            if (theta < -Math.PI) theta += 2.0 * Math.PI
            theta *= sn
            xLat = floor(ra * sin(theta) + XO + 0.5)
            yLon = floor(ro - ra * cos(theta) + YO + 0.5)
        } else {
            val xn = conLat - XO
            val yn = ro - conLon + YO
            var ra = sqrt(xn * xn + yn * yn)
            if (sn < 0.0) {
                ra = -ra
            }
            var alat = (re * sf / ra).pow(1.0 / sn)
            alat = 2.0 * atan(alat) - Math.PI * 0.5
            var theta : Double
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
            lat.postValue(alat * RADDEG)
            lon.postValue(alon * RADDEG)
        }
        restInit(lat.value!!, lon.value!!)
    }

    private suspend fun reverseGeoRest(latGeo : Double, lonGeo:Double) {
        val latlon = "${lonGeo},${latGeo}"

        val reverseGeocoderCo = repository.requestReverseGeo(
            MAP_REQUEST_DEFAULT,
            latlon,
            MAP_COORDINATE,
            DATA_TYPE_LOWER,
            MAP_ORDERS,
            MAP_CLIENT_KEY_ID,
            MAP_CLIENT_KEY
        )

        with(reverseGeocoderCo) {
            if (isSuccessful) {
                address.value = body() as ReverseGeocoder
                Log.e("",raw().request.url.toString())
            } else {
                weatherApplication.toastMessage(message())
                Log.e("", message())
            }
        }
    }

    private suspend fun fcstRest(){
        val nowFcst = repository.requestNowFcst(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            getTimeManager().urlNowDate(),
            getTimeManager().urlNowTime(),
            getXLat().toString(),
            getYLon().toString())

        with(nowFcst){
            if(isSuccessful){
                nowFcstData.postValue(body() as FcstData)
                Log.e("",raw().request.url.toString())
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
            }
        }
        val timeFcst = repository.requestFcst(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE_UPPER,
            getTimeManager().urlTimeFcstDate(),
            getTimeManager().urlTimeFcstTime(),
            getXLat().toString(),
            getYLon().toString())

        with(timeFcst){
            if(isSuccessful){
                timeFcstData.postValue(body() as FcstData)
                Log.e("",raw().request.url.toString())
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
            }
        }

        val adr = address.value!!
        val rainSky = repository.requestWeekRainSky(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_WEEK,
            DATA_TYPE_UPPER,
            dataConvert.landCodeGu(adr.results[adr.results.lastIndex].region.area1.name),
            urlWeekFcstTime())

        with(rainSky){
            if(isSuccessful){
                weekRainSkyData.postValue(body() as WeekRainSkyData)
                Log.e("",raw().request.url.toString())
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
            }
        }

        val rltmStation = repository.requestRltmStation(
            DATA_POTAL_SERVICE_KEY,
            DATA_TYPE_LOWER,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_AIR,
            "영등포구",
            DATE_TERM,
            RLTM_DATA_VERSION
        )

        with(rltmStation){
            if(isSuccessful){
                rltmStationData.postValue(body() as RltmStationIndex)
                Log.e("",raw().request.url.toString())
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
            }
        }

        val airQuality = repository.requestAirQuality(
            DATA_POTAL_SERVICE_KEY,
            DATA_TYPE_LOWER,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_AIR,
            getTimeManager().urlAirQualityDate(),
            AIR_CODE
        )

        with(airQuality){
            if(isSuccessful){
                airQualityData.postValue(body() as AirQualityIndex)
                Log.e("",raw().request.url.toString())
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
            }
        }
    }
    private fun restInit(latGeo : Double, lonGeo:Double){
        progressDialog.show()

        viewModelScope.launch {
            reverseGeoRest(latGeo, lonGeo)
            fcstRest()
        }

        if(progressDialog.isShowing)
            progressDialog.dismiss()
    }

    fun restReGe(latGeo : Double, lonGeo:Double){
        progressDialog.show()

        viewModelScope.launch {
            reverseGeoRest(latGeo, lonGeo)
        }

        if(progressDialog.isShowing)
            progressDialog.dismiss()
    }
}