package com.sjchoi.weather.common

import android.graphics.drawable.Drawable
import android.util.Log
import com.sjchoi.weather.R
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
        val errorMsg: String = when (code) {
            APPLICATION_ERROR -> { WeatherApplication.getWeatherApplication().getString(R.string.APPLICATION_ERROR) }
            DB_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.DB_ERROR) }
            NODATA_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.NODATA_ERROR) }
            HTTP_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.HTTP_ERROR) }
            SERVICETIME_OUT -> {WeatherApplication.getWeatherApplication().getString(R.string.SERVICETIME_OUT) }
            INVALID_REQUEST_PARAMETER_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.INVALID_REQUEST_PARAMETER_ERROR) }
            NO_MANDATORY_REQUEST_PARAMETERS_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.NO_MANDATORY_REQUEST_PARAMETERS_ERROR) }
            NO_OPENAPI_SERVICE_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.NO_OPENAPI_SERVICE_ERROR) }
            SERVICE_ACCESS_DENIED_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.SERVICE_ACCESS_DENIED_ERROR) }
            TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR) }
            LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR) }
            SERVICE_KEY_IS_NOT_REGISTERED_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.SERVICE_KEY_IS_NOT_REGISTERED_ERROR) }
            DEADLINE_HAS_EXPIRED_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.DEADLINE_HAS_EXPIRED_ERROR) }
            UNREGISTERED_IP_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.UNREGISTERED_IP_ERROR) }
            UNSIGNED_CALL_ERROR -> {WeatherApplication.getWeatherApplication().getString(R.string.UNSIGNED_CALL_ERROR) }
            else -> {WeatherApplication.getWeatherApplication().getString(R.string.UNKNOWN_ERROR) }
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
        var changeCode: Int = ((code.toInt() + 22.5 * 0.5) / 22.5).toInt()
        return when (changeCode) {
            1 -> WeatherApplication.getWeatherApplication().getString(R.string.NNE)
            2 -> WeatherApplication.getWeatherApplication().getString(R.string.NE)
            3 -> WeatherApplication.getWeatherApplication().getString(R.string.ENE)
            4 -> WeatherApplication.getWeatherApplication().getString(R.string.E)
            5 -> WeatherApplication.getWeatherApplication().getString(R.string.ESE)
            6 -> WeatherApplication.getWeatherApplication().getString(R.string.SE)
            7 -> WeatherApplication.getWeatherApplication().getString(R.string.SSE)
            8 -> WeatherApplication.getWeatherApplication().getString(R.string.S)
            9 -> WeatherApplication.getWeatherApplication().getString(R.string.SSW)
            10 -> WeatherApplication.getWeatherApplication().getString(R.string.SW)
            11 -> WeatherApplication.getWeatherApplication().getString(R.string.WSW)
            12 -> WeatherApplication.getWeatherApplication().getString(R.string.W)
            13 -> WeatherApplication.getWeatherApplication().getString(R.string.WNW)
            14 -> WeatherApplication.getWeatherApplication().getString(R.string.NW)
            15 -> WeatherApplication.getWeatherApplication().getString(R.string.NNW)
            else -> WeatherApplication.getWeatherApplication().getString(R.string.N)
        }
    }

    fun windPower(dir: String, code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.nowWindUnit, dir, code) }

    fun windPower(code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.windUnit, code) }

    fun fcstRainImgConvert(code: String): FcstImgEnum {
        return when (code) {
            "1" -> { FcstImgEnum.Rain }
            "2" -> { FcstImgEnum.Rain }//비+눈
            "3" -> { FcstImgEnum.Snow }
            "4" -> { FcstImgEnum.Rain }
            "5" -> { FcstImgEnum.Rain }
            "6" -> { FcstImgEnum.Rain }//비+눈
            "7" -> { FcstImgEnum.Snow }
            else -> { FcstImgEnum.Sun }
        }
    }

    fun fcstImgConvert(enumFcstImgEnum: FcstImgEnum): Drawable? {
        return when (enumFcstImgEnum) {
            FcstImgEnum.ClodeSun -> { WeatherApplication.getWeatherApplication().getDrawable(R.drawable.cloudsun) }
            FcstImgEnum.Cloude -> { WeatherApplication.getWeatherApplication().getDrawable(R.drawable.cloud) }
            FcstImgEnum.Rain -> { WeatherApplication.getWeatherApplication().getDrawable(R.drawable.rain) }
            FcstImgEnum.Snow -> { WeatherApplication.getWeatherApplication().getDrawable(R.drawable.snow) }
            else -> { WeatherApplication.getWeatherApplication().getDrawable(R.drawable.sunny) }
        }
    }

    fun skyConvert(code: String): String {
        return when (code) {
            SKY_SUN -> { WeatherApplication.getWeatherApplication().getString(R.string.sun) }
            SKY_CLOUDSUN -> { WeatherApplication.getWeatherApplication().getString(R.string.manycloud) }
            SKY_CLOUD -> { WeatherApplication.getWeatherApplication().getString(R.string.cloud) }
            else -> { WeatherApplication.getWeatherApplication().getString(R.string.sun) }
        }
    }

    fun skyImgEnum(code: String, fcstImgEnum: FcstImgEnum): FcstImgEnum {
        var fcstImg = if (fcstImgEnum == FcstImgEnum.Sun) {
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

        var splitString = code.chunked(2)
        return WeatherApplication.getWeatherApplication().applicationContext.getString(R.string.date,splitString[2],splitString[3])
    }

    fun mapAddressConvert(reverseGeocoder: ReverseGeocoder):String{
        with(reverseGeocoder){
            return if(this.status.code == 0) {
                "${results[0].region.area1.name} ${results[0].region.area2.name} ${results[0].land.name} ${results[0].land.number1}"
            }
            else{
                "주소를 찾을 수 없습니다"}
        }
    }

}