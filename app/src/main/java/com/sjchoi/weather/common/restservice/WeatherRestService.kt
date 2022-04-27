package com.sjchoi.weather.common.restservice

import com.sjchoi.weather.common.NOW_FCST
import com.sjchoi.weather.common.VILAGE_FCST
import com.sjchoi.weather.common.WEEK_RAIN_SKY
import com.sjchoi.weather.dataclass.fcstdata.FcstData
import com.sjchoi.weather.dataclass.fcstdata.WeekRainSkyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRestService {

    //단기예보
    @GET(VILAGE_FCST)
    fun requestFcst(@Query("serviceKey") serviceKey: String,
                    @Query("pageNo") pageNo:String,
                    @Query("numOfRows") numOfRows:String,
                    @Query("dataType") dataType:String,
                    @Query("base_date") base_date:String,
                    @Query("base_time") base_time:String,
                    @Query("nx") nx:String,
                    @Query("ny") ny:String): Call<FcstData>

    @GET(NOW_FCST)
    fun requestNowFcst(@Query("serviceKey") serviceKey: String,
                    @Query("pageNo") pageNo:String,
                    @Query("numOfRows") numOfRows:String,
                    @Query("dataType") dataType:String,
                    @Query("base_date") base_date:String,
                    @Query("base_time") base_time:String,
                    @Query("nx") nx:String,
                    @Query("ny") ny:String): Call<FcstData>

    @GET(WEEK_RAIN_SKY)
    fun requestWeekRainSky(@Query("serviceKey") serviceKey: String,
                       @Query("pageNo") pageNo:String,
                       @Query("numOfRows") numOfRows:String,
                       @Query("dataType") dataType:String,
                       @Query("regId") regId:String,
                       @Query("tmFc") tmFc:String): Call<WeekRainSkyData>
}