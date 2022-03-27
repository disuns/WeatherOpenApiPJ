package com.sjchoi.weather.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.adapter.TimeFcstAdapter
import com.sjchoi.weather.common.*
import com.sjchoi.weather.data.FcstData
import com.sjchoi.weather.data.TimeFcstData
import com.sjchoi.weather.databinding.FragmentTabBinding
import com.sjchoi.weather.enum.FcstImgEnum
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabFragment : BaseFragment<FragmentTabBinding>(FragmentTabBinding::inflate) {

    var tabEnum: WeatherTabEnum = WeatherTabEnum.None
    lateinit var timeFcstAdapter: TimeFcstAdapter
    lateinit var timeFcstLayoutManager: RecyclerView.LayoutManager

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

        when(tabEnum){
            WeatherTabEnum.Fcst->{
                binding.tabFcst.isVisible = true
                //nowFcstRest()
                timeFcstRest()
            }
            WeatherTabEnum.LifeIndex->{
                binding.tabFcst.isVisible=false
            }
            else->{}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun nowFcstRest() {
        val weatherService = RetrofitOkHttpManager.weatherRESTService

        val nowFcstCall: Call<FcstData> = weatherService.requestNowFcst(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE,
            TimeManager.urlNowDate(),
            TimeManager.urlNowTime(),
            "55",
            "127"
        )

        nowFcstCall.enqueue(object : Callback<FcstData> {
            override fun onResponse(call: Call<FcstData>, response: Response<FcstData>) {
                if (response.isSuccessful) {
                    val fcstData = response.body() as FcstData
                    if (fcstData.response.header.resultCode == NO_ERROR) {
                        nowDataSet(fcstData)
                    } else {
                        DataConvert.getDataConvert().dataPotalResultCode(fcstData.response.header.resultCode)
                    }
                }
            }

            override fun onFailure(call: Call<FcstData>, t: Throwable) {
                WeatherApplication.getWeatherApplication().toastMessage(t.message.toString())
            }
        })
    }

    fun timeFcstRest(){
        val weatherService = RetrofitOkHttpManager.weatherRESTService

        val timeFcstCall: Call<FcstData> = weatherService.requestFcst(
            DATA_POTAL_SERVICE_KEY,
            PAGE_NO_DEFAULT,
            NUM_OF_ROWS_DEFAULT,
            DATA_TYPE,
            TimeManager.urlNowDate(),
            TimeManager.urlFcstTime(),
            "55",
            "127"
        )

        timeFcstCall.enqueue(object :Callback<FcstData>{
            override fun onResponse(call: Call<FcstData>, response: Response<FcstData>) {
                if (response.isSuccessful) {
                    val fcstData = response.body() as FcstData
                    if(fcstData.response.header.resultCode == NO_ERROR) {
                        timeFcstAdapter = TimeFcstAdapter(timeDataSet(fcstData))
                        binding.timeFcstRV.adapter = timeFcstAdapter
                        timeFcstLayoutManager = LinearLayoutManager(WeatherApplication.getWeatherApplication().applicationContext, RecyclerView.HORIZONTAL,false)
                        binding.timeFcstRV.layoutManager = timeFcstLayoutManager
                    }else{
                        DataConvert.getDataConvert().dataPotalResultCode(fcstData.response.header.resultCode)
                    }
                }
            }

            override fun onFailure(call: Call<FcstData>, t: Throwable) {
                WeatherApplication.getWeatherApplication().toastMessage(t.message.toString())
            }
        })
    }

    fun weekFcstRest(){

    }

    fun nowDataSet(fcstData: FcstData){
        val fcstNow = fcstData.response.body
        val fcstItem = fcstNow.items.item
        var windDir = "0"
        var fcstImg = FcstImgEnum.None
        with(binding){
            for(i in fcstItem.indices){
                if((fcstItem[i].fcstTime.toInt()-fcstItem[i].baseTime.toInt())<100) {
                    when(fcstItem[i].category) {
                        "T1H" -> {
                            nowTempTV.text = DataConvert.getDataConvert().tempConvert(fcstItem[i].fcstValue)
                        }
                        "RN1" -> {
                            nowRainTV.text = DataConvert.getDataConvert().nowRainConvert(fcstItem[i].fcstValue)
                        }
                        "REH" -> {
                            nowWetTV.text = DataConvert.getDataConvert().nowWetConvert(fcstItem[i].fcstValue)
                        }
                        "VEC" -> {
                            windDir = DataConvert.getDataConvert().windDir(fcstItem[i].fcstValue)
                        }
                        "WSD" -> {
                            nowWindTV.text = DataConvert.getDataConvert().windPower(windDir,fcstItem[i].fcstValue)
                        }
                        "PTY"->{
                            fcstImg = DataConvert.getDataConvert().fcstRainImgConvert(fcstItem[i].fcstValue)
                        }
                        "SKY"->{
                            fcstImg = DataConvert.getDataConvert().skyImgEnum(fcstItem[i].fcstValue,fcstImg)
                            nowFcstIV.setImageDrawable(DataConvert.getDataConvert().fcstImgConvert(fcstImg))
                            nowFcstTV.text = DataConvert.getDataConvert().skyConvert(fcstItem[i].fcstValue)
                        }
                    }
                }
            }
        }
    }

    fun timeDataSet(fcstData: FcstData):List<TimeFcstData>{
        var fcstList = mutableListOf<TimeFcstData>()
        val fcstNow = fcstData.response.body
        val fcstItem = fcstNow.items.item
        var timeData = TimeFcstData()
        for(i in fcstItem.indices){
            if(timeData.fcstDate != fcstItem[i].fcstDate || timeData.fcstTime != fcstItem[i].fcstTime)
            {
                timeData = TimeFcstData()
                timeData.fcstTime = fcstItem[i].fcstTime
                timeData.fcstDate = fcstItem[i].fcstDate
            }

            when(fcstItem[i].category) {
                "TMP" -> {
                    timeData.temp = fcstItem[i].fcstValue
                }
                "VEC" -> {
                    timeData.windDir = fcstItem[i].fcstValue
                }
                "WSD" -> {
                    timeData.windPower = fcstItem[i].fcstValue
                }
                "SKY" -> {
                    timeData.sky = fcstItem[i].fcstValue
                }
                "PTY" -> {
                    timeData.rain = fcstItem[i].fcstValue
                }
                "POP" -> {
                    timeData.rainPer = fcstItem[i].fcstValue
                }
                "PCP" -> {
                    timeData.rainMm = fcstItem[i].fcstValue
                }
                "REH" -> {
                    timeData.wet = fcstItem[i].fcstValue
                    fcstList.add(timeData)
                }
                else -> {}
            }
        }

        return fcstList
    }

}