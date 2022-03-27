package com.sjchoi.weather.common

import com.sjchoi.weather.data.FcstData
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
}