package com.sjchoi.weather.dataclass

data class FcstBody(
    val dataType: String,
    val items: FcstItems,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class FcstItems(
    val item: MutableList<FcstItem>
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