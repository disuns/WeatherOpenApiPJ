package com.sjchoi.weather.dataclass.fcstdata

data class WeekRainSkyItems(
    val item: MutableList<WeekRainSkyItem>
)

data class FcstItems(
    val item: MutableList<FcstItem>
)