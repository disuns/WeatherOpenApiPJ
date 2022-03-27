package com.sjchoi.weather.common

import android.graphics.drawable.Drawable
import android.util.Log
import com.sjchoi.weather.R
import com.sjchoi.weather.enum.FcstImgEnum
import com.sjchoi.weather.enum.WindDirEnum

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

    fun rainPerConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.rainPerUnit, code) }

    fun tempConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.tempUnit, code) }

    fun nowWetConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.nowWetUnit, code) }

    fun wetConvert(code: String): String { return WeatherApplication.getWeatherApplication().getString(R.string.wetUnit, code) }

    fun nowRainConvert(code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.nowRainUnit, code) }

    fun rainConvert(code: String): String { return WeatherApplication.getWeatherApplication()
            .getString(R.string.rainUnit, code) }

    fun windDir(code: String): String {
        val changeCode: Int = ((code.toInt() + 22.5 * 0.5) / 22.5).toInt()
        return when (changeCode as WindDirEnum) {
            WindDirEnum.NNE -> WeatherApplication.getWeatherApplication().getString(R.string.NNE)
            WindDirEnum.NE -> WeatherApplication.getWeatherApplication().getString(R.string.NE)
            WindDirEnum.ENE -> WeatherApplication.getWeatherApplication().getString(R.string.ENE)
            WindDirEnum.E -> WeatherApplication.getWeatherApplication().getString(R.string.E)
            WindDirEnum.ESE -> WeatherApplication.getWeatherApplication().getString(R.string.ESE)
            WindDirEnum.SE -> WeatherApplication.getWeatherApplication().getString(R.string.SE)
            WindDirEnum.SSE -> WeatherApplication.getWeatherApplication().getString(R.string.SSE)
            WindDirEnum.S -> WeatherApplication.getWeatherApplication().getString(R.string.S)
            WindDirEnum.SSW -> WeatherApplication.getWeatherApplication().getString(R.string.SSW)
            WindDirEnum.SW -> WeatherApplication.getWeatherApplication().getString(R.string.SW)
            WindDirEnum.WSW -> WeatherApplication.getWeatherApplication().getString(R.string.WSW)
            WindDirEnum.W -> WeatherApplication.getWeatherApplication().getString(R.string.W)
            WindDirEnum.WNW -> WeatherApplication.getWeatherApplication().getString(R.string.WNW)
            WindDirEnum.NW -> WeatherApplication.getWeatherApplication().getString(R.string.NW)
            WindDirEnum.NNW -> WeatherApplication.getWeatherApplication().getString(R.string.NNW)
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
        if (fcstImgEnum == FcstImgEnum.Sun) {
            return when (code) {
                SKY_SUN -> { FcstImgEnum.Sun }
                SKY_CLOUDSUN -> { FcstImgEnum.ClodeSun }
                SKY_CLOUD -> { FcstImgEnum.Cloude }
                else -> { FcstImgEnum.Sun }
            }
        }
        return FcstImgEnum.Sun
    }

    fun timeDataConvert(code: String) : String{
        return WeatherApplication.getWeatherApplication().applicationContext.getString(R.string.time, code.substring(0,2))
    }

    fun dateConvert(){

    }

}