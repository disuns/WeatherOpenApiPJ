package com.sjchoi.weather.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sjchoi.weather.common.DATA_TYPE
import com.sjchoi.weather.common.VILAGE_SERVICE_KEY
import com.sjchoi.weather.data.ultraSrtNcst
import com.sjchoi.weather.databinding.FragmentTabBinding
import com.sjchoi.weather.https.RetrofitOkHttpManager
import retrofit2.Call
import retrofit2.Callback

class TabFragment : BaseFragment<FragmentTabBinding>(FragmentTabBinding::inflate) {
    companion object{
        fun newInstance() = TabFragment()
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
        selectUltraSrtNcst()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun selectUltraSrtNcst(){
        val weatherService = RetrofitOkHttpManager.weatherRESTService
        val call: Call<ultraSrtNcst> = weatherService.requestUltraSrtNcst(
            VILAGE_SERVICE_KEY,
            "1",
            "1000",
            DATA_TYPE,
            "20220314",
            "0600",
            "55",
            "127")

        call.enqueue(object : Callback<ultraSrtNcst> {
            override fun onResponse(
                call: retrofit2.Call<ultraSrtNcst>,
                response: retrofit2.Response<ultraSrtNcst>
            ) {
                if(response.isSuccessful){
                    Log.e("response", "succecs")
                }
            }
            override fun onFailure(call: retrofit2.Call<ultraSrtNcst>, t: Throwable) {
                Log.e("response", "fail")
            }
        })
    }
}