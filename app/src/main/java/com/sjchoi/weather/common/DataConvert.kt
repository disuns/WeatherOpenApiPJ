package com.sjchoi.weather.common

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import com.sjchoi.weather.R
import com.sjchoi.weather.dataclass.WeekDate
import com.sjchoi.weather.dataclass.reverseGeocoder.ReverseGeocoder
import com.sjchoi.weather.enum.FcstImgEnum

object DataConvert {
    private var instance: DataConvert? = null

    fun getDataConvert(): DataConvert {
        if (instance == null) {
            instance = this
        }

        return instance as DataConvert
    }

    fun dataPotalResultCode(code: String) {

        val errorMsg: String = with(WeatherApplication.getWeatherApplication()) {
            when (code) {
                APPLICATION_ERROR -> {
                    getString(R.string.APPLICATION_ERROR)
                }
                DB_ERROR -> {
                    getString(R.string.DB_ERROR)
                }
                NODATA_ERROR -> {
                    getString(R.string.NODATA_ERROR)
                }
                HTTP_ERROR -> {
                    getString(R.string.HTTP_ERROR)
                }
                SERVICETIME_OUT -> {
                    getString(R.string.SERVICETIME_OUT)
                }
                INVALID_REQUEST_PARAMETER_ERROR -> {
                    getString(R.string.INVALID_REQUEST_PARAMETER_ERROR)
                }
                NO_MANDATORY_REQUEST_PARAMETERS_ERROR -> {
                    getString(R.string.NO_MANDATORY_REQUEST_PARAMETERS_ERROR)
                }
                NO_OPENAPI_SERVICE_ERROR -> {
                    getString(R.string.NO_OPENAPI_SERVICE_ERROR)
                }
                SERVICE_ACCESS_DENIED_ERROR -> {
                    getString(R.string.SERVICE_ACCESS_DENIED_ERROR)
                }
                TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR -> {
                    getString(R.string.TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR)
                }
                LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR -> {
                    getString(R.string.LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR)
                }
                SERVICE_KEY_IS_NOT_REGISTERED_ERROR -> {
                    getString(R.string.SERVICE_KEY_IS_NOT_REGISTERED_ERROR)
                }
                DEADLINE_HAS_EXPIRED_ERROR -> {
                    getString(R.string.DEADLINE_HAS_EXPIRED_ERROR)
                }
                UNREGISTERED_IP_ERROR -> {
                    getString(R.string.UNREGISTERED_IP_ERROR)
                }
                UNSIGNED_CALL_ERROR -> {
                    getString(R.string.UNSIGNED_CALL_ERROR)
                }
                else -> {
                    getString(R.string.UNKNOWN_ERROR)
                }
            }
        }

        Log.e("convert", errorMsg)
        WeatherApplication.getWeatherApplication().toastMessage(errorMsg)
    }
    //이미지+날씨정보

    fun rainPerConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.perUnit, code) }

    fun tempConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.tempUnit, code) }

    fun nowWetConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.nowWetUnit, code) }

    fun wetConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.perUnit, code) }

    fun nowRainConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.nowRainUnit, code) }

    fun rainConvert(code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.rainUnit, code) }

    fun windDir(code: String): String {
        return with(WeatherApplication.getWeatherApplication()) {
            when (((code.toInt() + 22.5 * 0.5) / 22.5).toInt()) {
                getString(R.string.NUM1)
                    .toInt() -> getString(R.string.NNE)
                getString(R.string.NUM2)
                    .toInt() -> getString(R.string.NE)
                getString(R.string.NUM3)
                    .toInt() -> getString(R.string.ENE)
                getString(R.string.NUM4)
                    .toInt() -> getString(R.string.E)
                getString(R.string.NUM5)
                    .toInt() -> getString(R.string.ESE)
                getString(R.string.NUM6)
                    .toInt() -> getString(R.string.SE)
                getString(R.string.NUM7)
                    .toInt() -> getString(R.string.SSE)
                getString(R.string.NUM8)
                    .toInt() -> getString(R.string.S)
                getString(R.string.NUM9)
                    .toInt() -> getString(R.string.SSW)
                getString(R.string.NUM10)
                    .toInt() -> getString(R.string.SW)
                getString(R.string.NUM11)
                    .toInt() -> getString(R.string.WSW)
                getString(R.string.NUM12)
                    .toInt() -> getString(R.string.W)
                getString(R.string.NUM13)
                    .toInt() -> getString(R.string.WNW)
                getString(R.string.NUM14)
                    .toInt() -> getString(R.string.NW)
                getString(R.string.NUM15)
                    .toInt() -> getString(R.string.NNW)
                else -> getString(R.string.N)
            }
        }
    }

    fun windPower(dir: String, code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.nowWindUnit, dir, code) }

    fun windPower(code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.windUnit, code) }

    fun fcstRainImgConvert(code: String): FcstImgEnum {

        return with(WeatherApplication.getWeatherApplication()) {
            when (code) {
                getString(R.string.NUM1) -> {
                    FcstImgEnum.Rain
                }
                getString(R.string.NUM2) -> {
                    FcstImgEnum.Rain
                }//비+눈
                getString(R.string.NUM3) -> {
                    FcstImgEnum.Snow
                }
                getString(R.string.NUM4) -> {
                    FcstImgEnum.Rain
                }
                getString(R.string.NUM5) -> {
                    FcstImgEnum.Rain
                }
                getString(R.string.NUM6) -> {
                    FcstImgEnum.Rain
                }//비+눈
                getString(R.string.NUM7) -> {
                    FcstImgEnum.Snow
                }
                else -> {
                    FcstImgEnum.Sun
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun fcstImgConvert(enumFcstImgEnum: FcstImgEnum): Drawable? {
        return with(WeatherApplication.getWeatherApplication()) {
            when (enumFcstImgEnum) {
                FcstImgEnum.ClodeSun -> {
                    getDrawable(R.drawable.cloudsun)
                }
                FcstImgEnum.Cloude -> {
                    getDrawable(R.drawable.cloud)
                }
                FcstImgEnum.Rain -> {
                    getDrawable(R.drawable.rain)
                }
                FcstImgEnum.Snow -> {
                    getDrawable(R.drawable.snow)
                }
                else -> {
                    getDrawable(R.drawable.sunny)
                }
            }
        }
    }

    fun skyConvert(code: String): String {
        return with(WeatherApplication.getWeatherApplication()){
            when (code) {
                SKY_SUN -> { getString(R.string.sun) }
                SKY_CLOUDSUN -> { getString(R.string.manycloud) }
                SKY_CLOUD -> { getString(R.string.cloud) }
                else -> { getString(R.string.sun) }
            }
        }

    }

    fun skyImgEnum(code: String, fcstImgEnum: FcstImgEnum): FcstImgEnum {
        val fcstImg = if (fcstImgEnum == FcstImgEnum.Sun) {
            when (code) {
                SKY_SUN -> {
                    FcstImgEnum.Sun
                }
                SKY_CLOUDSUN -> {
                    FcstImgEnum.ClodeSun
                }
                SKY_CLOUD -> {
                    FcstImgEnum.Cloude
                }
                else -> {
                    FcstImgEnum.Sun
                }
            }
        }
        else fcstImgEnum
        return fcstImg
    }

    fun timeDataConvert(code: String) : String{
        return WeatherApplication.getWeatherApplication().applicationContext.getString(R.string.time, code.substring(0,2))
    }

    fun dateConvert(code : String) : String{
        val splitString = code.chunked(2)
        return WeatherApplication.getWeatherApplication().applicationContext.getString(R.string.date,splitString[2],splitString[3])
    }

    fun mapAddressConvert(reverseGeocoder: ReverseGeocoder):String{
        with(reverseGeocoder){
            return if(this.status.code == 0) {
                with(results[results.lastIndex]){
                    when(name){
                        "roadaddr"->{"${region.area1.name} ${region.area2.name} ${land.name} ${land.number1}"}
                        "addr"->{"${region.area1.name} ${region.area2.name} ${region.area3.name} ${land.number1}"}
                        else->{""}
                    }
                }
            }
            else{
//                val geocoder = Geocoder(WeatherApplication.getWeatherApplication().applicationContext)
//                geocoder
                WeatherApplication.getWeatherApplication().applicationContext.getString(R.string.UNKNOWN_ADDRESS)
            }
        }
    }

    fun landCodeGu(land:String):String{
        return with(WeatherApplication.getWeatherApplication()) {
            when(land){
                this.getString(R.string.land1)->{getString(R.string.landCode1)}
                getString(R.string.land2)->{getString(R.string.landCode1)}
                getString(R.string.land3)->{getString(R.string.landCode1)}
                getString(R.string.land4)->{getString(R.string.landCode2)}
                getString(R.string.land5)->{getString(R.string.landCode3)}
                getString(R.string.land6)->{getString(R.string.landCode3)}
                getString(R.string.land7)->{getString(R.string.landCode3)}
                getString(R.string.land8)->{getString(R.string.landCode4)}
                getString(R.string.land9)->{getString(R.string.landCode5)}
                getString(R.string.land10)->{getString(R.string.landCode5)}
                getString(R.string.land11)->{getString(R.string.landCode6)}
                getString(R.string.land12)->{getString(R.string.landCode7)}
                getString(R.string.land13)->{getString(R.string.landCode7)}
                getString(R.string.land14)->{getString(R.string.landCode8)}
                getString(R.string.land15)->{getString(R.string.landCode8)}
                getString(R.string.land16)->{getString(R.string.landCode8)}
                else->{getString(R.string.landCode9)}
            }
        }

        //중기예보 육상예보 REST
        //11B00000 서울, 인천,경기도
        //11D10000 강원도영서
        //11C20000 대전, 세종, 충청남도
        //11C10000 충청북도
        //11F20000 광주, 전라남도
        //11F10000 전라북도
        //11H10000 대구, 경상북도
        //11H20000 부산, 울산, 경상남도
        //11G0000 제주도
    }

    fun weekDateConvert(weekDate: WeekDate) : String{
        return WeatherApplication.getWeatherApplication().getString(R.string.weekDate, weekDate.month,weekDate.day,weekDate.dayOfWeek)
    }
}
