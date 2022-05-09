package com.sjchoi.weather.common.restservice

import com.sjchoi.weather.common.*
import com.sjchoi.weather.dataclass.datapotal.StationResponse
import com.sjchoi.weather.dataclass.datapotal.fcstdata.FcstData
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekRainSkyData
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.StationInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRestService {
    @GET(VILAGE_FCST)
    suspend fun requestFcst(@Query("serviceKey") serviceKey: String,
                            @Query("pageNo") pageNo:String,
                            @Query("numOfRows") numOfRows:String,
                            @Query("dataType") dataType:String,
                            @Query("base_date") base_date:String,
                            @Query("base_time") base_time:String,
                            @Query("nx") nx:String,
                            @Query("ny") ny:String): Response<FcstData>

    @GET(NOW_FCST)
    suspend fun requestNowFcst(@Query("serviceKey") serviceKey: String,
                               @Query("pageNo") pageNo:String,
                               @Query("numOfRows") numOfRows:String,
                               @Query("dataType") dataType:String,
                               @Query("base_date") base_date:String,
                               @Query("base_time") base_time:String,
                               @Query("nx") nx:String,
                               @Query("ny") ny:String): Response<FcstData>

    @GET(WEEK_RAIN_SKY)
    suspend fun requestWeekRainSky(@Query("serviceKey") serviceKey: String,
                                   @Query("pageNo") pageNo:String,
                                   @Query("numOfRows") numOfRows:String,
                                   @Query("dataType") dataType:String,
                                   @Query("regId") regId:String,
                                   @Query("tmFc") tmFc:String): Response<WeekRainSkyData>

    @GET(AIR_QUALITY_FRCST)
    suspend fun requestAirQuality(@Query("serviceKey") serviceKey: String,
                                  @Query("returnType") returnType:String,
                                     @Query("pageNo") pageNo:String,
                                     @Query("numOfRows") numOfRows:String,
                                     @Query("searchDate") searchDate:String,
                                     @Query("InformCode") InformCode:String): Response<AirQualityIndex>

    @GET(RLTM_STATION)
    suspend fun requestRltmStation(@Query("serviceKey") serviceKey: String,
                                   @Query("returnType") returnType:String,
                                     @Query("pageNo") pageNo:String,
                                     @Query("numOfRows") numOfRows:String,
                                     @Query("stationName") stationName:String,
                                     @Query("dataTerm") dataTerm:String,
                                    @Query("ver")ver:String): Response<RltmStationIndex>

    @GET(STATION_FIND)
    suspend fun requestStationFind(@Query("serviceKey") serviceKey: String,
                                   @Query("returnType") returnType:String,
                                   @Query("tmX") tmX:String,
                                   @Query("tmY") tmY:String,
                                   @Query("ver")ver:String): Response<StationInfo>
}