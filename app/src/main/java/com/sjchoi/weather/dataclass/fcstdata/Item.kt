package com.sjchoi.weather.dataclass.fcstdata

data class WeekRainSkyItem(
    val regId: String,
    val rnSt10: Int,
    val rnSt3Am: Int,
    val rnSt3Pm: Int,
    val rnSt4Am: Int,
    val rnSt4Pm: Int,
    val rnSt5Am: Int,
    val rnSt5Pm: Int,
    val rnSt6Am: Int,
    val rnSt6Pm: Int,
    val rnSt7Am: Int,
    val rnSt7Pm: Int,
    val rnSt8: Int,
    val rnSt9: Int,
    val wf10: String,
    val wf3Am: String,
    val wf3Pm: String,
    val wf4Am: String,
    val wf4Pm: String,
    val wf5Am: String,
    val wf5Pm: String,
    val wf6Am: String,
    val wf6Pm: String,
    val wf7Am: String,
    val wf7Pm: String,
    val wf8: String,
    val wf9: String
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