package com.sjchoi.weather.dataclass.datapotal

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

data class AirQualityBody(
    val items: MutableList<AirQualityItem>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class RltmStationBody(
    val items: MutableList<RltmStationItem>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class StationBody(
    val items: MutableList<StationItem>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)