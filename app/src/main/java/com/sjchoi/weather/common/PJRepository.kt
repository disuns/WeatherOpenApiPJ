package com.sjchoi.weather.common

import android.util.Log
import com.sjchoi.weather.dataclass.datapotal.fcstdata.FcstData
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekRainSkyData
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.StationInfo
import com.sjchoi.weather.dataclass.reverseGeocoder.ReverseGeocoder
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class PJRepository {
    private val weatherApplication = WeatherApplication.getWeatherApplication()

    private val weatherService = RetrofitOkHttpManager.weatherRESTService
    private val mapService = RetrofitOkHttpManager.mapRESTService

    suspend fun requestFcst(@Query("base_date") base_date:String,
                            @Query("base_time") base_time:String,
                            @Query("nx") nx:String,
                            @Query("ny") ny:String): FcstData? {
        val response = weatherService.requestFcst(DATA_POTAL_SERVICE_KEY,PAGE_NO_DEFAULT,NUM_OF_ROWS_DEFAULT,DATA_TYPE_UPPER, base_date, base_time, nx, ny)

        with(response){
            if(isSuccessful){
                Log.e("",raw().request.url.toString())
                return body() as FcstData
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
                return null
            }
        }
    }

    suspend fun requestNowFcst(@Query("base_date") base_date:String,
                               @Query("base_time") base_time:String,
                               @Query("nx") nx:String,
                               @Query("ny") ny:String): FcstData? {
        val response =weatherService.requestNowFcst(DATA_POTAL_SERVICE_KEY, PAGE_NO_DEFAULT, NUM_OF_ROWS_DEFAULT, DATA_TYPE_UPPER, base_date, base_time, nx, ny)

        with(response){
            if(isSuccessful){
                Log.e("",raw().request.url.toString())
                return body() as FcstData
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
                return null
            }
        }
    }

    suspend fun requestWeekRainSky(@Query("regId") regId:String,
                                   @Query("tmFc") tmFc:String): WeekRainSkyData? {
        val response = weatherService.requestWeekRainSky(DATA_POTAL_SERVICE_KEY, PAGE_NO_DEFAULT, NUM_OF_ROWS_WEEK, DATA_TYPE_UPPER, regId, tmFc)

        with(response){
            if(isSuccessful){
                Log.e("",raw().request.url.toString())
                return body() as WeekRainSkyData
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
                return null
            }
        }
    }

    suspend fun requestAirQuality(@Query("searchDate") searchDate:String): AirQualityIndex? {
        val response = weatherService.requestAirQuality(DATA_POTAL_SERVICE_KEY, DATA_TYPE_LOWER, PAGE_NO_DEFAULT, NUM_OF_ROWS_AIR, searchDate, AIR_CODE)

        with(response){
            if(isSuccessful){
                Log.e("",raw().request.url.toString())
                return body() as AirQualityIndex
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
                return null
            }
        }
    }

    suspend fun requestRltmStation(@Query("stationName") stationName:String): RltmStationIndex? {
        val response = weatherService.requestRltmStation(DATA_POTAL_SERVICE_KEY, DATA_TYPE_LOWER, PAGE_NO_DEFAULT, NUM_OF_ROWS_AIR, stationName, DATE_TERM, RLTM_DATA_VERSION)
        with(response){
            if(isSuccessful){
                Log.e("",raw().request.url.toString())
                return body() as RltmStationIndex
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
                return null
            }
        }
    }

    suspend fun requestStationFind(@Query("tmX") tmX:String,
                                   @Query("tmY") tmY:String): StationInfo? {
        val response = weatherService.requestStationFind(DATA_POTAL_SERVICE_KEY, DATA_TYPE_LOWER, tmX, tmY, STATION_VERSION)

        with(response){
            if(isSuccessful){
                Log.e("",raw().request.url.toString())
                return body() as StationInfo
            }else{
                weatherApplication.toastMessage(message())
                Log.e("",message())
                return null
            }
        }
    }

    suspend fun requestReverseGeo(@Query("coords", encoded = true) coords:String): ReverseGeocoder? {

        val response = mapService.requestReverseGeoCo(MAP_REQUEST_DEFAULT,coords, MAP_COORDINATE_DEFAULT,MAP_COORDINATE_TM, DATA_TYPE_LOWER, MAP_ORDERS, MAP_CLIENT_KEY_ID, MAP_CLIENT_KEY)
        with(response) {
            if (isSuccessful) {
                Log.e("",raw().request.url.toString())
                return body() as ReverseGeocoder
            } else {
                weatherApplication.toastMessage(message())
                Log.e("", message())
                return null
            }
        }
    }

}