package com.sjchoi.weather.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sjchoi.weather.common.DATA_POTAL_SERVICE_KEY
import com.sjchoi.weather.common.DATA_TYPE
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.data.FcstData
import com.sjchoi.weather.data.TodayFcstData
import com.sjchoi.weather.databinding.FragmentTabBinding
import com.sjchoi.weather.enum.WeatherTabEnum
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabFragment : BaseFragment<FragmentTabBinding>(FragmentTabBinding::inflate) {

    var tabEnum: WeatherTabEnum = WeatherTabEnum.None

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

        with(binding) {
        }
        fcstRest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun fcstRest() {
        val weatherService = RetrofitOkHttpManager.weatherRESTService

        val todayFcstCall: Call<TodayFcstData> = weatherService.requestTodayFcst(
            DATA_POTAL_SERVICE_KEY,
            "1",
            "1000",
            DATA_TYPE,
            "20220322",
            "1400",
            "55",
            "127"
        )

        val timeFcstCall: Call<FcstData> = weatherService.requestFcst(
            DATA_POTAL_SERVICE_KEY,
            "1",
            "1000",
            DATA_TYPE,
            "20220322",
            "1400",
            "55",
            "127"
        )

        todayFcstCall.enqueue(object : Callback<TodayFcstData> {
            override fun onResponse(call: Call<TodayFcstData>, response: Response<TodayFcstData>) {
                if (response.isSuccessful) {
                    val fcstData = response.body() as TodayFcstData
                    if(fcstData.response.header.resultCode == "00") {
                        Log.e("","success")
                    }else{
                        DataConvert.dataPotalResultCode(fcstData.response.header.resultCode)
                    }
                }
            }

            override fun onFailure(call: Call<TodayFcstData>, t: Throwable) {
                Log.e("",t.message.toString())
            }
        })

        timeFcstCall.enqueue(object :Callback<FcstData>{
            override fun onResponse(call: Call<FcstData>, response: Response<FcstData>) {
                if (response.isSuccessful) {
                    val fcstData = response.body() as FcstData
                    if(fcstData.response.header.resultCode == "00") {
                        Log.e("","success")
                    }else{
                        DataConvert.dataPotalResultCode(fcstData.response.header.resultCode)
                    }
                }
            }

            override fun onFailure(call: Call<FcstData>, t: Throwable) {
                Log.e("",t.message.toString())
            }
        })
    }

    fun timeFcstRest(){
        val weatherService = RetrofitOkHttpManager.weatherRESTService

    }

}