package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.R
import com.sjchoi.weather.adapter.TimeFcstAdapter
import com.sjchoi.weather.adapter.WeekFcstAdapter
import com.sjchoi.weather.common.*
import com.sjchoi.weather.common.manager.TimeManager
import com.sjchoi.weather.databinding.FragmentFcstBinding
import com.sjchoi.weather.dataclass.datapotal.fcstdata.*
import com.sjchoi.weather.enum.FcstImgEnum
import kotlin.math.abs

class FcstFragment : BaseFragment<FragmentFcstBinding>(FragmentFcstBinding::inflate) {

    private var timeFcstAdapter: TimeFcstAdapter? = null
    private var weekFcstAdapter: WeekFcstAdapter? = null
    private lateinit var weekFcstLayoutManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance() = FcstFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            getNowFcstData().observe(viewLifecycleOwner) { nowDataSet(it) }
            getTimeFcstData().observe(viewLifecycleOwner) { timeDataSet(it) }
            getWeekRainSkyData().observe(viewLifecycleOwner) { weekDataSet(it) }
        }
    }


    private fun nowDataSet(fcstData : FcstData){
        if (viewModel.checkNowFcstData()) {
            val fcstItem = fcstData.response.body.items.item
            var windDir = NUM0
            var fcstImg = FcstImgEnum.None
            with(binding) {
                for (i in fcstItem.indices) {
                    if ((fcstItem[i].fcstTime.toInt() - fcstItem[i].baseTime.toInt()) < 100) {
                        with(dataConvert) {
                            when (fcstItem[i].category) {
                                TMP_NOW -> {
                                    nowTempTV.text =
                                        tempConvert(fcstItem[i].fcstValue)
                                }
                                RAIN_MM_NOW -> {
                                    nowRainTV.text = nowRainConvert(fcstItem[i].fcstValue)
                                }
                                WET -> {
                                    nowWetTV.text = nowWetConvert(fcstItem[i].fcstValue)
                                }
                                WIND_DIR -> {
                                    windDir = windDir(fcstItem[i].fcstValue)
                                }
                                WIND_POWER -> {
                                    nowWindTV.text = windPower(windDir, fcstItem[i].fcstValue)
                                }
                                RAIN_TYPE -> {
                                    fcstImg = fcstRainImgConvert(fcstItem[i].fcstValue)
                                }
                                SKY -> {
                                    fcstImg =skyImgEnum(fcstItem[i].fcstValue, fcstImg)
                                    nowFcstIV.setImageDrawable(fcstImgConvert(fcstImg))
                                    nowFcstTV.text = skyConvert(fcstItem[i].fcstValue)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun timeDataSet(fcstData : FcstData){
        timeFcstAdapter = TimeFcstAdapter(timeDataList(fcstData))
        with(binding.timeFcstVP2){
            adapter = timeFcstAdapter
            offscreenPageLimit = NUM4.toInt()
            setPageTransformer { page, position ->
                pageTransForm(page, position)
            }
        }
    }

    private fun timeDataList(fcstData : FcstData):List<TimeFcstData>{
        val fcstList = mutableListOf<TimeFcstData>()
        if(viewModel.checkTimeFcstData()) {
            val fcstItem = fcstData.response.body.items.item
            var timeData = TimeFcstData()
            for (i in fcstItem.indices) {
                with(fcstItem[i]) {
                    if (timeData.fcstDate != fcstDate || timeData.fcstTime != fcstTime) {
                        timeData = TimeFcstData()
                        timeData.fcstTime = fcstTime
                        timeData.fcstDate = fcstDate
                    }

                    when (category) {
                        TMP_TIME -> {
                            timeData.temp = fcstValue
                        }
                        WIND_DIR -> {
                            timeData.windDir = fcstValue
                        }
                        WIND_POWER -> {
                            timeData.windPower = fcstValue
                        }
                        SKY -> {
                            timeData.sky = fcstValue
                        }
                        RAIN_TYPE -> {
                            timeData.rain = fcstValue
                        }
                        RAIN_PER -> {
                            timeData.rainPer = fcstValue
                        }
                        RAIN_MM -> {
                            timeData.rainMm = fcstValue
                        }
                        WET -> {
                            timeData.wet = fcstValue
                            fcstList.add(timeData)
                        }
                        else -> {}
                    }
                }
            }
        }
        return fcstList
    }

    private fun weekDataList(weekRainSkyData: WeekRainSkyData): MutableList<WeekFcstData> {
        val weekList = mutableListOf<WeekFcstData>()
        if(viewModel.checkWeekRainSkyData()) {
            val weekData = weekRainSkyData.response.body.items.item
            val timeManager = TimeManager.getTimeManager()
            for(i in weekData.indices){
                with(weekData[i]){
                    weekList.add(setWeekFcstData(timeManager.getFcstWeekUIDate(NUM3.toInt()),rnSt3Am.toString(), rnSt3Pm.toString(), wf3Am, wf3Pm))
                    weekList.add(setWeekFcstData(timeManager.getFcstWeekUIDate(NUM4.toInt()),rnSt4Am.toString(), rnSt4Pm.toString(), wf4Am, wf4Pm))
                    weekList.add(setWeekFcstData(timeManager.getFcstWeekUIDate(NUM5.toInt()),rnSt5Am.toString(), rnSt5Pm.toString(), wf5Am, wf5Pm))
                    weekList.add(setWeekFcstData(timeManager.getFcstWeekUIDate(NUM6.toInt()),rnSt6Am.toString(), rnSt6Pm.toString(), wf6Am, wf6Pm))
                    weekList.add(setWeekFcstData(timeManager.getFcstWeekUIDate(NUM7.toInt()),rnSt7Am.toString(), rnSt7Pm.toString(), wf7Am, wf7Pm))
                }
            }
        }
        return weekList
    }

    private fun setWeekFcstData(weekDate: WeekDate, rainAM:String, rainPM:String, skyAM:String, skyPM:String): WeekFcstData {
        val weekFcstData = WeekFcstData()
        with(weekFcstData){
            this.weekDate = weekDate
            rainAm = rainAM
            rainPm = rainPM
            skyAm = skyAM
            skyPm = skyPM
        }
        return weekFcstData
    }

    private fun weekDataSet(fcstData : WeekRainSkyData){
        weekFcstAdapter = WeekFcstAdapter(weekDataList(fcstData))
        binding.weekFcstRV.adapter = weekFcstAdapter
        weekFcstLayoutManager = LinearLayoutManager(weatherApplication.applicationContext, RecyclerView.VERTICAL,false)
        binding.weekFcstRV.layoutManager = weekFcstLayoutManager
    }

    private fun pageTransForm(page:View, position : Float){
        val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.FcstOffsetBetweenPages).toFloat()
        val myOffset = position * -(NUM2.toInt() * offsetBetweenPages)
        with(page) {
            when {
                position < -NUM1.toInt() -> {
                    translationX = -myOffset
                }
                position <= NUM1.toInt() -> {
                    // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                    val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
                    translationX = myOffset
                    scaleY = scaleFactor
                    alpha = scaleFactor
                }
                else -> {
                    alpha = 0f
                    translationX = myOffset
                }
            }
        }
    }
}