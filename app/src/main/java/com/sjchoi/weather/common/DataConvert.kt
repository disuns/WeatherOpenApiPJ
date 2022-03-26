package com.sjchoi.weather.common

import android.graphics.drawable.Drawable
import android.util.Log
import com.sjchoi.weather.R
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
            "01" -> { WeatherApplication.getWeatherApplication().getString(R.string.APPLICATION_ERROR)
            }
            "02" -> {WeatherApplication.getWeatherApplication().getString(R.string.DB_ERROR)
            }
            "03" -> {WeatherApplication.getWeatherApplication().getString(R.string.NODATA_ERROR)
            }
            "04" -> {WeatherApplication.getWeatherApplication().getString(R.string.HTTP_ERROR)
            }
            "05" -> {WeatherApplication.getWeatherApplication().getString(R.string.SERVICETIME_OUT)
            }
            "10" -> {WeatherApplication.getWeatherApplication().getString(R.string.INVALID_REQUEST_PARAMETER_ERROR)
            }
            "11" -> {WeatherApplication.getWeatherApplication().getString(R.string.NO_MANDATORY_REQUEST_PARAMETERS_ERROR)
            }
            "12" -> {WeatherApplication.getWeatherApplication().getString(R.string.NO_OPENAPI_SERVICE_ERROR)
            }
            "20" -> {WeatherApplication.getWeatherApplication().getString(R.string.SERVICE_ACCESS_DENIED_ERROR)
            }
            "21" -> {WeatherApplication.getWeatherApplication().getString(R.string.TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR)
            }
            "22" -> {WeatherApplication.getWeatherApplication().getString(R.string.LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR)
            }
            "30" -> {WeatherApplication.getWeatherApplication().getString(R.string.SERVICE_KEY_IS_NOT_REGISTERED_ERROR)
            }
            "31" -> {WeatherApplication.getWeatherApplication().getString(R.string.DEADLINE_HAS_EXPIRED_ERROR)
            }
            "32" -> {WeatherApplication.getWeatherApplication().getString(R.string.UNREGISTERED_IP_ERROR)
            }
            "33" -> {WeatherApplication.getWeatherApplication().getString(R.string.UNSIGNED_CALL_ERROR)
            }
            else -> {WeatherApplication.getWeatherApplication().getString(R.string.UNKNOWN_ERROR)
            }
        }
        Log.e("Convert", errorMsg)
    }
    //이미지+날씨정보

    fun rainPerConvert(code: String): String {
        return WeatherApplication.getWeatherApplication().getString(R.string.rainPerUnit, code)
    }

    fun tempConvert(code: String): String {
        return WeatherApplication.getWeatherApplication().getString(R.string.tempUnit, code)
    }

    fun nowWetConvert(code: String): String {
        return WeatherApplication.getWeatherApplication().getString(R.string.nowWetUnit, code)
    }

    fun wetConvert(code: String): String {
        return WeatherApplication.getWeatherApplication().getString(R.string.wetUnit, code)
    }

    fun nowRainConvert(code: String): String {
        return WeatherApplication.getWeatherApplication()
            .getString(R.string.nowRainUnit, code)
    }

    fun rainConvert(code: String): String {
        return WeatherApplication.getWeatherApplication()
            .getString(R.string.rainUnit, code)
    }

    fun windDir(code: String): String {
        val changeCode: Int = ((code.toInt() + 22.5 * 0.5) / 22.5).toInt()
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

    fun windPower(dir: String, code: String): String {
        return WeatherApplication.getWeatherApplication()
            .getString(R.string.nowWindUnit, dir, code)
    }

    fun windPower(code: String): String {
        return WeatherApplication.getWeatherApplication()
            .getString(R.string.windUnit, code)
    }

    fun fcstRainImgConvert(code: String): FcstImgEnum {
        return when (code) {
            "1" -> {
                FcstImgEnum.Rain
            }
            "2" -> {
                FcstImgEnum.Rain
            }//비+눈
            "3" -> {
                FcstImgEnum.Snow
            }
            "4" -> {
                FcstImgEnum.Rain
            }
            "5" -> {
                FcstImgEnum.Rain
            }
            "6" -> {
                FcstImgEnum.Rain
            }//비+눈
            "7" -> {
                FcstImgEnum.Snow
            }
            else -> {
                FcstImgEnum.Sun
            }
        }
    }

    fun fcstImgConvert(enumFcstImgEnum: FcstImgEnum): Drawable? {
        return when (enumFcstImgEnum) {
            FcstImgEnum.ClodeSun -> {
                WeatherApplication.getWeatherApplication().getDrawable(R.drawable.cloudsun)
            }
            FcstImgEnum.Cloude -> {
                WeatherApplication.getWeatherApplication().getDrawable(R.drawable.cloud)
            }
            FcstImgEnum.Rain -> {
                WeatherApplication.getWeatherApplication().getDrawable(R.drawable.rain)
            }
            FcstImgEnum.Snow -> {
                WeatherApplication.getWeatherApplication().getDrawable(R.drawable.snow)
            }
            else -> {
                WeatherApplication.getWeatherApplication().getDrawable(R.drawable.sunny)
            }
        }
    }

    fun skyConvert(code: String): String {
        return when (code) {
            "1" -> {
                WeatherApplication.getWeatherApplication().getString(R.string.sun)
            }
            "3" -> {
                WeatherApplication.getWeatherApplication().getString(R.string.manycloud)
            }
            "4" -> {
                WeatherApplication.getWeatherApplication().getString(R.string.cloud)
            }
            else -> {
                WeatherApplication.getWeatherApplication().getString(R.string.sun)
            }
        }
    }

    fun skyImgEnum(code: String, fcstImgEnum: FcstImgEnum): FcstImgEnum {
        if (fcstImgEnum == FcstImgEnum.Sun) {
            return when (code) {
                "1" -> {
                    FcstImgEnum.Sun
                }
                "3" -> {
                    FcstImgEnum.ClodeSun
                }
                "4" -> {
                    FcstImgEnum.Cloude
                }
                else -> {
                    FcstImgEnum.Sun
                }
            }
        }
        return FcstImgEnum.Sun
    }

    fun timeDataConvert(code: String) : String{
        return WeatherApplication.getWeatherApplication().applicationContext.getString(R.string.time, code.substring(0,2))
    }

}