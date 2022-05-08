package com.sjchoi.weather.https

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sjchoi.weather.common.*
import com.sjchoi.weather.common.restservice.MapRestService
import com.sjchoi.weather.common.restservice.WeatherRestService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitOkHttpManager {
    private val gson : Gson = GsonBuilder().setLenient().create()

    private var okHttpClient: OkHttpClient

    private val retrofitBuilderDataPotal : Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(DATA_POTAL_URL)

    private val retrofitBuiderMap : Retrofit.Builder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(MAPS_URL)

    val weatherRESTService: WeatherRestService
        get() = retrofitBuilderDataPotal.build().create(WeatherRestService::class.java)

    val mapRESTService: MapRestService
        get() = retrofitBuiderMap.build().create(MapRestService::class.java)

    init {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                val newRequest: Request = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }).addInterceptor(RetryInterceptor())
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).build()
        retrofitBuilderDataPotal.client(okHttpClient)
        retrofitBuiderMap.client(okHttpClient)
    }

    private class RetryInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            var response: Response = chain.proceed(request)
            var tryCount = NUM0.toInt()
            val maxLimit = NUM2.toInt()
            while (!response.isSuccessful && tryCount < maxLimit) {
                tryCount++
                response = chain.proceed(request)
            }
            return response
        }
    }
}