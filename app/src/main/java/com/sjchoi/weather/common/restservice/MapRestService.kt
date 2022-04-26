package com.sjchoi.weather.common.restservice

import com.sjchoi.weather.common.MAP_REVERSE_GEOCODE
import com.sjchoi.weather.dataclass.reverseGeocoder.ReverseGeocoder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapRestService {

    @GET(MAP_REVERSE_GEOCODE)
    fun requestReverseGeo(@Query("request") request: String,
                          @Query("coords", encoded = true) coords:String,
                          @Query("sourcecrs", encoded = true) sourcecrs:String,
                          @Query("output") output:String,
                          @Query("orders") orders:String,
                          @Query("X-NCP-APIGW-API-KEY-ID") apikeyid:String,
                          @Query("X-NCP-APIGW-API-KEY") apikey:String): Call<ReverseGeocoder>
}