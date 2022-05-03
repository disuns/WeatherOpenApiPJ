package com.sjchoi.weather.dataclass.datapotal.fcstdata

data class WeekFcstData(
    var weekDate : WeekDate = WeekDate(),
    var date : String = "",
    var rainAm:String = "",
    var rainPm:String = "",
    var skyAm:String = "",
    var skyPm:String = ""
)
