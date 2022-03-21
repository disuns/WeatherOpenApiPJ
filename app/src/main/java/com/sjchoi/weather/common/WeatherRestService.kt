package com.sjchoi.weather.common

import com.sjchoi.weather.data.FcstData
import com.sjchoi.weather.data.TodayFcstData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRestService {

    //단기예보
    @GET(VILAGE_FCST)
    fun requestFcst(@Query("ServiceKey") serviceKey: String,
                            @Query("pageNo") pageNo:String,
                            @Query("numOfRows") numOfRows:String,
                            @Query("dataType") dataType:String,
                            @Query("base_data") base_data:String,
                            @Query("base_time") base_time:String,
                            @Query("nx") nx:String,
                            @Query("ny") ny:String): Call<FcstData>

    @GET(TODAY_FCST)
    fun requestTodayFcst(@Query("ServiceKey") serviceKey: String,
                    @Query("pageNo") pageNo:String,
                    @Query("numOfRows") numOfRows:String,
                    @Query("dataType") dataType:String,
                    @Query("base_data") base_data:String,
                    @Query("base_time") base_time:String,
                    @Query("nx") nx:String,
                    @Query("ny") ny:String): Call<TodayFcstData>
}