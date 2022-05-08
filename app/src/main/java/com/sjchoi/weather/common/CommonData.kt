package com.sjchoi.weather.common

/**
 * 데이터 변환 체크용
 */
const val NO_ERROR = "00"
const val APPLICATION_ERROR = "01"
const val DB_ERROR = "02"
const val NODATA_ERROR = "03"
const val HTTP_ERROR = "04"
const val SERVICETIME_OUT = "05"
const val INVALID_REQUEST_PARAMETER_ERROR = "10"
const val NO_MANDATORY_REQUEST_PARAMETERS_ERROR = "11"
const val NO_OPENAPI_SERVICE_ERROR = "12"
const val SERVICE_ACCESS_DENIED_ERROR = "20"
const val TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR = "21"
const val LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR = "22"
const val SERVICE_KEY_IS_NOT_REGISTERED_ERROR = "30"
const val DEADLINE_HAS_EXPIRED_ERROR = "31"
const val UNREGISTERED_IP_ERROR = "32"
const val UNSIGNED_CALL_ERROR = "33"

const val SKY = "SKY"
const val TMP_TIME = "TMP"
const val TMP_NOW = "T1H"
const val WIND_DIR = "VEC"
const val WIND_POWER = "WSD"
const val RAIN_TYPE = "PTY"
const val RAIN_PER = "POP"
const val RAIN_MM = "PCP"
const val WET = "REH"
const val RAIN_MM_NOW = "RN1"

const val ROAD_ADDR = "roadaddr"
const val ADDR="addr"

/**
 * Rest 및 Delay 관련
 */
const val CONNECT_TIME_OUT = 20L
const val LOADING_TIME = 1000L
const val READ_TIME_OUT = 15L

const val PAGE_NO_DEFAULT = "1"
const val NUM_OF_ROWS_DEFAULT = "1000"
const val NUM_OF_ROWS_AIR = "100"
const val NUM_OF_ROWS_WEEK = "10"

const val DATA_POTAL_URL = "http://apis.data.go.kr/"
const val MAPS_URL = "https://naveropenapi.apigw.ntruss.com/"

const val DATA_TYPE_UPPER = "JSON"
const val DATA_TYPE_LOWER = "json"

const val DATE_TERM = "DAILY"
const val RLTM_DATA_VERSION = "1.3"

const val AIR_CODE = "PM10"

const val DATA_POTAL_SERVICE_KEY = "XaZsPnx+pwzxoELxydXTYGgdm/4grf0F8GEnwfH4F+0+NOqPp4qjBGgEHFgCdBc9GEZmWUaF2p1AFoSylmuT0g=="

const val MAP_REQUEST_DEFAULT = "coordToaddr"
const val MAP_COORDINATE = "epsg:4326"
const val MAP_ORDERS = "addr,roadaddr"
const val MAP_CLIENT_KEY_ID = "47cfbsdnq2"
const val MAP_CLIENT_KEY = "f8EYncOzdp6LVjHoVOxl6VkzpAteQFlcgv2yrihT"
const val MAP_REVERSE_GEOCODE = "/map-reversegeocode/v2/gc"
//const val MAP_GEOCODE = "/map-geocode/v2/geocode"

/**
 * 단기예보
 */
const val NOW_FCST = "/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"
const val VILAGE_FCST = "/1360000/VilageFcstInfoService_2.0/getVilageFcst"
/**
 * 중기예보
 */
const val WEEK_RAIN_SKY = "/1360000/MidFcstInfoService/getMidLandFcst"

/**
 * 대기오염 정보
 */
const val AIR_QUALITY_FRCST = "/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth" //대기질 예보통보 조회
const val RLTM_STATION = "/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" //측정소별 실시간 측정정보 조회

/**
 * 나머지
 */

const val NUM0 = "0"
const val NUM1 = "1"
const val NUM2 = "2"
const val NUM3 = "3"
const val NUM4 = "4"
const val NUM5 = "5"
const val NUM6 = "6"
const val NUM7 = "7"
const val NUM8 = "8"
const val NUM9 = "9"
const val NUM10 = "10"
const val NUM11 = "11"
const val NUM12 = "12"
const val NUM13 = "13"
const val NUM14 = "14"
const val NUM15 = "15"

