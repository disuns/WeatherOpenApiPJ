package com.sjchoi.weather.common

import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class PJRepository {
    private val weatherService = RetrofitOkHttpManager.weatherRESTService
    private val mapService = RetrofitOkHttpManager.mapRESTService

    suspend fun requestFcst(@Query("serviceKey") serviceKey: String,
                            @Query("pageNo") pageNo:String,
                            @Query("numOfRows") numOfRows:String,
                            @Query("dataType") dataType:String,
                            @Query("base_date") base_date:String,
                            @Query("base_time") base_time:String,
                            @Query("nx") nx:String,
                            @Query("ny") ny:String)= weatherService.requestFcst(serviceKey,pageNo,numOfRows,dataType, base_date, base_time, nx, ny)

    suspend fun requestNowFcst(@Query("serviceKey") serviceKey: String,
                               @Query("pageNo") pageNo:String,
                               @Query("numOfRows") numOfRows:String,
                               @Query("dataType") dataType:String,
                               @Query("base_date") base_date:String,
                               @Query("base_time") base_time:String,
                               @Query("nx") nx:String,
                               @Query("ny") ny:String) = weatherService.requestNowFcst(serviceKey, pageNo, numOfRows, dataType, base_date, base_time, nx, ny)

    suspend fun requestWeekRainSky(@Query("serviceKey") serviceKey: String,
                                   @Query("pageNo") pageNo:String,
                                   @Query("numOfRows") numOfRows:String,
                                   @Query("dataType") dataType:String,
                                   @Query("regId") regId:String,
                                   @Query("tmFc") tmFc:String) = weatherService.requestWeekRainSky(serviceKey, pageNo, numOfRows, dataType, regId, tmFc)

    suspend fun requestAirQuality(@Query("serviceKey") serviceKey: String,
                                  @Query("returnType") returnType:String,
                                  @Query("pageNo") pageNo:String,
                                  @Query("numOfRows") numOfRows:String,
                                  @Query("searchDate") searchDate:String,
                                  @Query("InformCode") InformCode:String) = weatherService.requestAirQuality(serviceKey, returnType, pageNo, numOfRows, searchDate, InformCode)

    suspend fun requestRltmStation(@Query("serviceKey") serviceKey: String,
                                   @Query("returnType") returnType:String,
                                   @Query("pageNo") pageNo:String,
                                   @Query("numOfRows") numOfRows:String,
                                   @Query("stationName") stationName:String,
                                   @Query("dataTerm") dataTerm:String,
                                    @Query("ver")ver:String) = weatherService.requestRltmStation(serviceKey, returnType, pageNo, numOfRows, stationName, dataTerm, ver)

    suspend fun requestReverseGeo(@Query("request") request: String,
                                  @Query("coords", encoded = true) coords:String,
                                  @Query("sourcecrs", encoded = true) sourcecrs:String,
                                  @Query("output") output:String,
                                  @Query("orders") orders:String,
                                  @Query("X-NCP-APIGW-API-KEY-ID") apikeyid:String,
                                  @Query("X-NCP-APIGW-API-KEY") apikey:String) = mapService.requestReverseGeoCo(request,coords, sourcecrs, output, orders, apikeyid, apikey)
}