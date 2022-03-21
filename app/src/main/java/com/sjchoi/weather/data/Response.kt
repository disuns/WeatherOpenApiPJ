package com.sjchoi.weather.data

data class FcstResponse(
    val header: Header,
    val body : FcstBody
)

data class TodayFcstRespnse(
    val header: Header,
    val body: TodayFcstBody
)