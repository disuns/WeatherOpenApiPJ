package com.sjchoi.weather.common

import com.sjchoi.weather.dataclass.reverseGeocoder.ReverseGeocoder
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class PJRepository {
    private val weatherService = RetrofitOkHttpManager.weatherRESTService
    private val mapService = RetrofitOkHttpManager.mapRESTService

    suspend fun requestFcstCo(@Query("serviceKey") serviceKey: String,
                                      @Query("pageNo") pageNo:String,
                                      @Query("numOfRows") numOfRows:String,
                                      @Query("dataType") dataType:String,
                                      @Query("base_date") base_date:String,
                                      @Query("base_time") base_time:String,
                                      @Query("nx") nx:String,
                                      @Query("ny") ny:String)= weatherService.requestFcstCo(serviceKey,pageNo,numOfRows,dataType, base_date, base_time, nx, ny)

    suspend fun requestNowFcstCo(@Query("serviceKey") serviceKey: String,
                                 @Query("pageNo") pageNo:String,
                                 @Query("numOfRows") numOfRows:String,
                                 @Query("dataType") dataType:String,
                                 @Query("base_date") base_date:String,
                                 @Query("base_time") base_time:String,
                                 @Query("nx") nx:String,
                                 @Query("ny") ny:String) = weatherService.requestNowFcstCo(serviceKey, pageNo, numOfRows, dataType, base_date, base_time, nx, ny)

    suspend fun requestWeekRainSkyCo(@Query("serviceKey") serviceKey: String,
                                     @Query("pageNo") pageNo:String,
                                     @Query("numOfRows") numOfRows:String,
                                     @Query("dataType") dataType:String,
                                     @Query("regId") regId:String,
                                     @Query("tmFc") tmFc:String) = weatherService.requestWeekRainSkyCo(serviceKey, pageNo, numOfRows, dataType, regId, tmFc)

    suspend fun requestReverseGeoCo(@Query("request") request: String,
                                    @Query("coords", encoded = true) coords:String,
                                    @Query("sourcecrs", encoded = true) sourcecrs:String,
                                    @Query("output") output:String,
                                    @Query("orders") orders:String,
                                    @Query("X-NCP-APIGW-API-KEY-ID") apikeyid:String,
                                    @Query("X-NCP-APIGW-API-KEY") apikey:String) = mapService.requestReverseGeoCo(request,coords, sourcecrs, output, orders, apikeyid, apikey)
}