package com.sjchoi.weather.data

data class FcstBody(
    val dataType: String,
    val items: FcstItems,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class FcstItems(
    val item: List<FcstItem>
)

data class FcstItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val fcstDate : String,
    val fcstTime: String,
    val fcstValue: String,
)


data class TodayFcstBody(
    val dataType: String,
    val items:TodayFcstItems,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class TodayFcstItems(
    val item: List<TodayFcstItem>
)

data class TodayFcstItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val obsrValue : String
)