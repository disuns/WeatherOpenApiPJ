package com.sjchoi.weather.dataclass.fcstdata

data class WeekRainSkyBody(
    val dataType: String,
    val items: WeekRainSkyItems,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class FcstBody(
    val dataType: String,
    val items: FcstItems,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)