package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.sjchoi.weather.R
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.FragmentIndexBinding
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex

class IndexFragment : BaseFragment<FragmentIndexBinding>(FragmentIndexBinding::inflate) {

    companion object {
        fun newInstance() = IndexFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRltmStationIndex().observe(viewLifecycleOwner){rltmStationDataSet(it)}
        viewModel.getAirQualityIndex().observe(viewLifecycleOwner){airQualityDataSet(it)}
    }

    private fun rltmStationDataSet(rltmStationIndex: RltmStationIndex) {
        if(viewModel.checkRltmStationData()){
            val rltmItem = rltmStationIndex.response.body.items[0]
            with(binding){
                viewModel.getAddress().observe(viewLifecycleOwner){
                    rltmTitle.text = dataConvert.rltmTitle("영등포구")
                    //rltmTitle.text = resources.getString(R.string.rltmStation, it.results[it.results.lastIndex].region.area2.name)
                }
                rltmDateTV.text = resources.getString(R.string.stationTime, rltmItem.dataTime)
                rltmSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        with(rltmItem) {
                            when (position) {
                                resources.getString(R.string.NUM0).toInt() -> {
                                    setCardView(position,khaiValue, khaiGrade, null)
                                }
                                resources.getString(R.string.NUM1).toInt() -> {
                                    setCardView(
                                        position,
                                        pm25Value,
                                        pm25Grade,
                                        pm25Flag
                                    )
                                }
                                resources.getString(R.string.NUM2).toInt() -> {
                                    setCardView(
                                        position,
                                        pm10Value,
                                        pm10Grade,
                                        pm10Flag
                                    )
                                }
                                resources.getString(R.string.NUM3).toInt() -> {
                                    setCardView(
                                        position,
                                        o3Value,
                                        o3Grade,
                                        o3Flag
                                    )
                                }
                                resources.getString(R.string.NUM4).toInt() -> {
                                    setCardView(
                                        position,
                                        coValue,
                                        coGrade,
                                        coFlag
                                    )
                                }
                                resources.getString(R.string.NUM5).toInt() -> {
                                    setCardView(
                                        position,
                                        no2Value,
                                        no2Grade,
                                        no2Flag
                                    )
                                }
                                else -> {
                                    setCardView(
                                        position,
                                        so2Value,
                                        so2Grade,
                                        so2Flag
                                    )
                                }
                            }
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }
        }
    }

    private fun setCardView(position:Int, value:String, grade: String, flag: String? ){
        with(binding){
            with(dataConvert){
                rltmCardTV1.text=rltmValueConvert(position,value)
                rltmCardTV2.text = rltmGradeConvert(grade)
                val flagString = when(flag.isNullOrBlank()){
                    true->{"-"}
                    else->{flag}
                }
                rltmCardTV3.text = resources.getString(R.string.flag,flagString)
            }
        }
    }

    private fun airQualityDataSet(airQualityIndex: AirQualityIndex) {
        if(viewModel.checkAirQualityData()){
            val airQuality = airQualityIndex.response.body.items[0]
            with(binding){
                airDateAndCode.text = airQuality.dataTime
            }
        }
    }
}