package com.sjchoi.weather.dataclass.reverseGeocoder

data class ReverseGeocoder(
    val results: List<Result>,
    val status: Status
)

data class Status(
    val code: Int,
    val message: String,
    val name: String
)

data class Result(
    val code: Code,
    val land: Land,
    val name: String,
    val region: Region
)

data class Region(
    val area0: Area,
    val area1: Area1,
    val area2: Area,
    val area3: Area,
    val area4: Area
)

data class Land(
    val addition0: Addition,
    val addition1: Addition,
    val addition2: Addition,
    val addition3: Addition,
    val addition4: Addition,
    val coords: Coords,
    val name: String,
    val number1: String,
    val number2: String,
    val type: String
)

data class Coords(
    val center: Center
)

data class Code(
    val id: String,
    val mappingId: String,
    val type: String
)

data class Center(
    val crs: String,
    val x: Double,
    val y: Double
)

data class Area(
    val coords: Coords,
    val name: String
)

data class Area1(
    val coords: Coords,
    val name: String,
    val alias : String
)

data class Addition(
    val type: String,
    val value: String
)