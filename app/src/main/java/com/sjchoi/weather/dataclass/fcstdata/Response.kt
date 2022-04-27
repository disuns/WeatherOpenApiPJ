package com.sjchoi.weather.dataclass.fcstdata

data class FcstResponse(
    val header: Header,
    val body : FcstBody
)

data class WeekRainSkyRespons(
    val header: Header,
    val body: WeekRainSkyBody
)