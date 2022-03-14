package com.sjchoi.weather.data

data class ultraSrtNcst(
    val response: Response
)
data class Response(
    val body: Body,
    val header: Header
)
data class Header(
    val resultCode: String,
    val resultMsg: String
)
data class Body(
    val dataType: String,
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)
data class Items(
    val item: List<Item>
)
data class Item(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val obsrValue: String
)

data class ultraSrtFcst(
    val asd:String
)

data class vilageFcst(
    val asd:String
)

data class fcstVersion(
    val asd:String
)

