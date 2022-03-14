package com.sjchoi.weather.common

import com.sjchoi.weather.data.ultraSrtNcst
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRestService {

    @GET(VILAGE_FCST_URL+ ULTRA_SRT_NCST)
    fun requestUltraSrtNcst(@Query("ServiceKey") servicekey: String,
                            @Query("pageNo") pageNo:String,
                            @Query("numOfRows") numOfRows:String,
                            @Query("dataType") dataType:String,
                            @Query("base_data") base_data:String,
                            @Query("base_time") base_time:String,
                            @Query("nx") nx:String,
                            @Query("ny") ny:String): Call<ultraSrtNcst>
}