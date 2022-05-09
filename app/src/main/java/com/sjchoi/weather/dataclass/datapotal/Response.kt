package com.sjchoi.weather.dataclass.datapotal

data class FcstResponse(
    val header: Header,
    val body : FcstBody
)

data class WeekRainSkyRespons(
    val header: Header,
    val body: WeekRainSkyBody
)

data class AirQualityResponse(
    val body: AirQualityBody,
    val header: Header
)

data class RltmStationResponse(
    val body: RltmStationBody,
    val header: Header
)

data class StationResponse(
    val body: StationBody,
    val header: Header
)