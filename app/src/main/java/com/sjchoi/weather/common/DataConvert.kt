package com.sjchoi.weather.common

import android.util.Log

object DataConvert {
    private var instance : DataConvert? = null

    fun getDataConvert() : DataConvert{
        if(instance == null){
            instance = this
        }

        return instance as DataConvert
    }

    fun dataPotalResultCode(code : String){
        val errorMsg : String = when(code){
            "01"->{"어플리케이션 에러"}
            "02"->{"데이터베이스 에러"}
            "03"->{"데이터없음 에러"}
            "04"->{"HTTP 에러"}
            "05"->{"서비스 연결실패 에러"}
            "10"->{"잘못된 요청 파라메터 에러"}
            "11"->{"필수요청 파라메터가 없음"}
            "12"->{"해당 오픈 API서비스가 없거나 폐긴됨"}
            "20"->{"서비스 접근거부"}
            "21"->{"일시적으로 사용할 수 없는 서비스 키"}
            "22"->{"서비스 요청제한횟수 초과에러"}
            "30"->{"등록되지 않은 서비스키"}
            "31"->{"기한만료된 서비스키"}
            "32"->{"등록되지 않은 IP"}
            "33"->{"서명되지 않은 호출"}
            else->{"기타에러"}
        }
        Log.e("Convert",errorMsg)
    }

    fun vilageCategoryConvert(category: String, code: String) : String{
        return when(category){
            "POP"->{"강수확률 : ${code}%"}
            "PTY"->{categoryPTY(code)}
            "PCP"->{categoryRN1PCP(code)}
            "REH"->{"습도 : ${code}%"}
            "SNO"->{categorySNO(code)}
            "SKY"->{categorySKY(code)}
            "TMP"->{"1시간 기온 : ${code}℃"}
            "TMN"->{"일 최저기온 : ${code}℃"}
            "TMX"->{"일 최고기온 : ${code}℃"}
            "UUU"->{categoryUUU(code)}
            "VVV"->{categoryVVV(code)}
            "WAV"->{"파고 : ${code}M"}
            "VEC"->{"풍향 : ${code}deg"}
            "WSD"->{"풍속 : ${code}m/s"}
            "T1H"->{"기온 : ${code}℃"}
            "RN1"->{categoryRN1PCP(code)}
            "LGT"->{categoryLGT(code)}
            else -> {"잘못된 카테고리입니다"}
        }
    }

    private fun categorySKY(code : String) : String {
        return when (code) {
            "1" -> { "하늘상태 : 맑음" }
            "3" -> { "하늘상태 : 구름많음"}
            "4" -> { "하늘상태 : 흐림"}
            else ->{"잘못된 코드입니다"}
        }
    }

    private fun categoryPTY(code : String) : String {
        return when (code) {
            "0" -> { "강수형태 : 없음" }
            "1" -> { "강수형태 : 비" }
            "2" -> { "강수형태 : 비/눈" }
            "3" -> { "강수형태 : 눈" }
            "4" -> { "강수형태 : 소나기" }
            "5" -> { "강수형태 : 빗방울" }
            "6" -> { "강수형태 : 빗방울눈날림" }
            "7" -> { "강수형태 : 눈날림" }
            else ->{"잘못된 코드입니다"}
        }
    }

    private fun categoryRN1PCP(code : String) : String {
        val rainfall = if(code.toFloat()<1.0f)
            "1.0mm 미만"
        else if(code.toFloat() >= 1.0f && code.toFloat() < 30.0f )
            "${code.toFloat()}mm"
        else if(code.toFloat() >= 30.0f && code.toFloat() < 50.0f)
            "30.0mm ~ 50.0mm"
        else if(code.toFloat() >= 50.0)
            "50.0 mm 이상"
        else
            "강수없음"

        return "1시간 강수량 : $rainfall"
    }

    private fun categorySNO(code : String) : String {
        val snow = if(code.toFloat()<1.0f)
            "1.0cm 미만"
        else if(code.toFloat() >= 1.0f && code.toFloat() < 5.0f )
            "${code.toFloat()}cm"
        else if(code.toFloat() >= 5.0)
            "5.0 cm 이상"
        else
            "적설없음"

        return "1시간 신적설 : $snow"
    }

    private fun categoryLGT(code : String) : String {
        return "낙뢰 : 에너지밀도(${code.toFloat()}KA/km2"
    }

    private fun categoryUUU(code:String) : String{
        return if(code.toFloat()>0.0f)
            "동"
        else
            "서"
    }

    private fun categoryVVV(code: String) : String{
        return if(code.toFloat()>0.0f)
            "북"
        else
            "남"
    }
}