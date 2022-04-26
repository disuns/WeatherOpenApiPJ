package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.adapter.TimeFcstAdapter
import com.sjchoi.weather.adapter.WeekFcstAdapter
import com.sjchoi.weather.common.*
import com.sjchoi.weather.dataclass.fcstdata.TimeFcstData
import com.sjchoi.weather.databinding.FragmentTabBinding
import com.sjchoi.weather.dataclass.FcstData
import com.sjchoi.weather.enum.FcstImgEnum
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.viewmodel.WeatherViewModel

class TabFragment : BaseFragment<FragmentTabBinding>(FragmentTabBinding::inflate) {

    var tabEnum: WeatherTabEnum = WeatherTabEnum.None
    var timeFcstAdapter: TimeFcstAdapter? = null
    var weekFcstAdapter: WeekFcstAdapter? = null
    lateinit var timeFcstLayoutManager: RecyclerView.LayoutManager
    lateinit var weekFcstLayoutManager: RecyclerView.LayoutManager

    private lateinit var viewModel :WeatherViewModel

    companion object {
        fun newInstance(weatherTabEnum: WeatherTabEnum): TabFragment {
            val fragment = TabFragment()
            val bundle = Bundle()
            bundle.putSerializable("tabEnum", weatherTabEnum)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabEnum = arguments?.getSerializable("tabEnum") as WeatherTabEnum

       viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        when(tabEnum){
            WeatherTabEnum.Fcst->{
                viewModel.getNowFcstData().observe(viewLifecycleOwner){ nowDataSet(it) }

                viewModel.getTimeFcstData().observe(viewLifecycleOwner){ timeDataSet(it) }
            }
            WeatherTabEnum.LifeIndex->{
            }
            else->{}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun nowDataSet(fcstData : FcstData){
        if (viewModel.checkNowFcstData()) {
            val fcstNow = fcstData.response.body
            val fcstItem = fcstNow.items.item
            var windDir = "0"
            var fcstImg = FcstImgEnum.None
            with(binding) {
                for (i in fcstItem.indices) {
                    if ((fcstItem[i].fcstTime.toInt() - fcstItem[i].baseTime.toInt()) < 100) {
                        when (fcstItem[i].category) {
                            TMP_NOW -> {
                                nowTempTV.text =
                                    DataConvert.getDataConvert().tempConvert(fcstItem[i].fcstValue)
                            }
                            RAIN_MM_NOW -> {
                                nowRainTV.text = DataConvert.getDataConvert()
                                    .nowRainConvert(fcstItem[i].fcstValue)
                            }
                            WET -> {
                                nowWetTV.text = DataConvert.getDataConvert()
                                    .nowWetConvert(fcstItem[i].fcstValue)
                            }
                            WIND_DIR -> {
                                windDir =
                                    DataConvert.getDataConvert().windDir(fcstItem[i].fcstValue)
                            }
                            WIND_POWER -> {
                                nowWindTV.text = DataConvert.getDataConvert()
                                    .windPower(windDir, fcstItem[i].fcstValue)
                            }
                            RAIN_TYPE -> {
                                fcstImg = DataConvert.getDataConvert()
                                    .fcstRainImgConvert(fcstItem[i].fcstValue)
                            }
                            SKY -> {
                                fcstImg = DataConvert.getDataConvert()
                                    .skyImgEnum(fcstItem[i].fcstValue, fcstImg)
                                nowFcstIV.setImageDrawable(
                                    DataConvert.getDataConvert().fcstImgConvert(fcstImg)
                                )
                                nowFcstTV.text =
                                    DataConvert.getDataConvert().skyConvert(fcstItem[i].fcstValue)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun timeDataSet(fcstData : FcstData){
        timeFcstAdapter = TimeFcstAdapter(timeDataList(fcstData))
        binding.timeFcstRV.adapter = timeFcstAdapter
        timeFcstLayoutManager = LinearLayoutManager(WeatherApplication.getWeatherApplication().applicationContext, RecyclerView.HORIZONTAL,false)
        binding.timeFcstRV.layoutManager = timeFcstLayoutManager
    }

    private fun timeDataList(fcstData : FcstData):List<TimeFcstData>{
        var fcstList = mutableListOf<TimeFcstData>()
        if(viewModel.checkTimeFcstData()) {
            val fcstNow = fcstData.response.body
            val fcstItem = fcstNow.items.item
            var timeData = TimeFcstData()
            for (i in fcstItem.indices) {
                if (timeData.fcstDate != fcstItem[i].fcstDate || timeData.fcstTime != fcstItem[i].fcstTime) {
                    timeData = TimeFcstData()
                    timeData.fcstTime = fcstItem[i].fcstTime
                    timeData.fcstDate = fcstItem[i].fcstDate
                }

                when (fcstItem[i].category) {
                    TMP_TIME -> {
                        timeData.temp = fcstItem[i].fcstValue
                    }
                    WIND_DIR -> {
                        timeData.windDir = fcstItem[i].fcstValue
                    }
                    WIND_POWER -> {
                        timeData.windPower = fcstItem[i].fcstValue
                    }
                    SKY -> {
                        timeData.sky = fcstItem[i].fcstValue
                    }
                    RAIN_TYPE -> {
                        timeData.rain = fcstItem[i].fcstValue
                    }
                    RAIN_PER -> {
                        timeData.rainPer = fcstItem[i].fcstValue
                    }
                    RAIN_MM -> {
                        timeData.rainMm = fcstItem[i].fcstValue
                    }
                    WET -> {
                        timeData.wet = fcstItem[i].fcstValue
                        fcstList.add(timeData)
                    }
                    else -> {}
                }
            }

        }

        return fcstList
    }

    private fun weekDataList(){

    }

    private fun weekDataSet(){
        binding.weekFcstRV.adapter = weekFcstAdapter
        weekFcstLayoutManager = LinearLayoutManager(WeatherApplication.getWeatherApplication().applicationContext, RecyclerView.VERTICAL,false)
        binding.weekFcstRV.layoutManager = weekFcstLayoutManager
    }
}