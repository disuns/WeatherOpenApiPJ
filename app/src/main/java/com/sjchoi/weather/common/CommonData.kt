package com.sjchoi.weather.common

const val PERMISSIONS_REQUEST = 1001
const val CONNECT_TIME_OUT = 20L
const val READ_TIME_OUT = 15L

const val PAGE_NO_DEFAULT = "1"
const val NUM_OF_ROWS_DEFAULT = "1000"

const val DATA_POTAL_URL = "http://apis.data.go.kr/"
const val MAPS_URL = ""

const val DATA_TYPE = "JSON"

const val DATA_POTAL_SERVICE_KEY = "XaZsPnx+pwzxoELxydXTYGgdm/4grf0F8GEnwfH4F+0+NOqPp4qjBGgEHFgCdBc9GEZmWUaF2p1AFoSylmuT0g=="
/**
 * 단기예보
 */
const val NOW_FCST = "/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"
const val VILAGE_FCST = "/1360000/VilageFcstInfoService_2.0/getVilageFcst"

//onst val Mid

/**
 * 대기오염 정보
 */
const val MINU_DUST_FRCST = "/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth" //대기질 예보통보 조회
const val MINU_DUST_WEEK_FRCST = "/B552584/ArpltnInforInqireSvc/getMinuDustWeekFrcstDspth" //초 미세먼지 주간예보 조회
const val MSRSTN_ACCTO_RLTM = "/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" //측정소별 실시간 측정정보 조회
const val UNITY_AIR_ENVRN_INDEX = "/B552584/ArpltnInforInqireSvc/getUnityAirEnvrnIdexSnstiveAboveMsrstnList" //통합대기환경지수 나쁨 이상 측정소 목록 조회
const val CTPRVN_RLTM = "/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty" //시도별 실시간 측정정보 조회

/**
 * 생활기상지수
 */
const val UV_IDX = "/1360000/LivingWthrIdxServiceV2/getUVIdxV2" //자외선 지수 조회
const val AIR_DIFFUSION_IDX = "/1360000/LivingWthrIdxServiceV2/getAirDiffusionIdxV2" //대기 확산지수 조회
const val FREEZE_IDX = "/1360000/LivingWthrIdxServiceV2/getFreezeIdxV2" //동파 가능 지수 조회
const val SEN_TA_IDX = "/1360000/LivingWthrIdxServiceV2/getSenTaIdxV2" //체감온도(여름철)조회

