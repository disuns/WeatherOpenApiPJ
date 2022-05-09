package com.sjchoi.weather.dataclass.datapotal

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

data class AirQualityItem(
    val actionKnack: String,
    val dataTime: String,
    val imageUrl1: String,
    val imageUrl2: String,
    val imageUrl3: String,
    val imageUrl4: String,
    val imageUrl5: String,
    val imageUrl6: String,
    val imageUrl7: String,
    val imageUrl8: String,
    val imageUrl9: String,
    val informCause: String,
    val informCode: String,
    val informData: String,
    val informGrade: String,
    val informOverall: String
)

data class RltmStationItem(
    val coFlag: String,
    val coGrade: String,
    val coValue: String,
    val dataTime: String,
    val khaiGrade: String,
    val khaiValue: String,
    val no2Flag: String,
    val no2Grade: String,
    val no2Value: String,
    val o3Flag: String,
    val o3Grade: String,
    val o3Value: String,
    val pm10Flag: String,
    val pm10Grade: String,
    val pm10Value: String,
    val pm10Value24: String,
    val pm25Flag: String,
    val pm25Grade: String,
    val pm25Value: String,
    val pm25Value24: String,
    val so2Flag: String,
    val so2Grade: String,
    val so2Value: String
)

data class StationItem(
    val tm : Double,
    val addr : String,
    val stationName : String
)