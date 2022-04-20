package com.sjchoi.weather.dataclass

data class FcstResponse(
    val header: Header,
    val body : FcstBody
)