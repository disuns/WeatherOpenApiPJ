package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.R
import com.sjchoi.weather.adapter.AirRecyclerViewAdapter
import com.sjchoi.weather.adapter.ViewpagerImageAdapter
import com.sjchoi.weather.common.*
import com.sjchoi.weather.databinding.FragmentIndexBinding
import com.sjchoi.weather.dataclass.datapotal.AirQualityItem
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import com.sjchoi.weather.dataclass.datapotal.indexdata.RltmStationIndex
import kotlin.math.abs

class IndexFragment : BaseFragment<FragmentIndexBinding>(FragmentIndexBinding::inflate) {

    private var airLayoutManager = mutableListOf<RecyclerView.LayoutManager>()

    companion object {
        fun newInstance() = IndexFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel){
            getRltmStationIndex().observe(viewLifecycleOwner){rltmStationDataSet(it)}
            getAirQualityIndex().observe(viewLifecycleOwner){airQualityDataSet(it)}
        }
    }

    private fun rltmStationDataSet(rltmStationIndex: RltmStationIndex) {
        if(viewModel.checkRltmStationData()){
            val rltmItem = rltmStationIndex.response.body.items[NUM0.toInt()]
            with(binding){

                viewModel.getStationInfoData().observe(viewLifecycleOwner){
                    when(viewModel.checkStationInfo()){
                        true->{ rltmTitle.text = dataConvert.rltmTitle( it.response.body.items[NUM0.toInt()].stationName)}
                        else->{}
                    }
                }

                setCardView(NUM0.toInt(), rltmItem.khaiValue,rltmItem.khaiGrade,null)
                rltmDateTV.text = dataConvert.rltmStationDate(rltmItem.dataTime)
                rltmSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        with(rltmItem) {
                            when (position) {
                                NUM0.toInt() -> {
                                    setCardView(position,khaiValue, khaiGrade, null)
                                }
                                NUM1.toInt() -> {
                                    setCardView(
                                        position,
                                        pm25Value,
                                        pm25Grade,
                                        pm25Flag
                                    )
                                }
                                NUM2.toInt() -> {
                                    setCardView(
                                        position,
                                        pm10Value,
                                        pm10Grade,
                                        pm10Flag
                                    )
                                }
                                NUM3.toInt() -> {
                                    setCardView(
                                        position,
                                        o3Value,
                                        o3Grade,
                                        o3Flag
                                    )
                                }
                                NUM4.toInt() -> {
                                    setCardView(
                                        position,
                                        coValue,
                                        coGrade,
                                        coFlag
                                    )
                                }
                                NUM5.toInt() -> {
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
                    true->{resources.getString(R.string.nullString)}
                    else->{flag}
                }
                rltmCardTV3.text = rltmFlag(flagString)
            }
        }
    }

    private fun airQualityDataSet(airQualityIndex: AirQualityIndex) {
        if(viewModel.checkAirQualityData()){
            val airQuality = airQualityIndex.response.body.items[0]
            with(binding){
                airDateAndCode.text = dataConvert.airDateAndCode(airQuality.dataTime, airQuality.informCode)
                informOverall.text = airQuality.informOverall
                informCause.text = airQuality.informCause
                val actionKnacktNullCheck =when(airQuality.actionKnack.isNullOrBlank()){
                    true->resources.getString(R.string.nullString)
                    else->airQuality.actionKnack
                }

                actionKnack.text = dataConvert.actionKnact(actionKnacktNullCheck)
                airListView(airQuality)
                airQualityImageSet(airQuality)
            }
        }
    }

    private fun airListView(airQuality: AirQualityItem){
        val informGrades = dataConvert.airInformGrade(airQuality.informGrade)
        val chunkeInformGrades = informGrades.chunked(NUM7.toInt()).toMutableList()
        val adapterList = mutableListOf<AirRecyclerViewAdapter>()

        for(i in chunkeInformGrades.indices){
            val recyclerview = AirRecyclerViewAdapter(chunkeInformGrades[i])
            adapterList.add(recyclerview)
            airLayoutManager.add(LinearLayoutManager(weatherApplication.applicationContext, RecyclerView.VERTICAL,false))
        }


        with(binding){
            for(i in adapterList.indices){
                val airRecycler = when(i){
                    NUM0.toInt()->{airRecyclerView1}
                    NUM1.toInt()->{airRecyclerView2}
                    else->{airRecyclerView3}
                }
                with(airRecycler){
                    adapter =adapterList[i]
                    layoutManager=airLayoutManager[i]
                }
            }
        }
    }

    private fun airQualityImageSet(airQuality: AirQualityItem) {
        val imageUrlPm10 = mutableListOf<String>()

        with(imageUrlPm10){
            add(airQuality.imageUrl1)
            add(airQuality.imageUrl2)
            add(airQuality.imageUrl3)
        }
        val imagePm10Adapter = ViewpagerImageAdapter(this,imageUrlPm10)
        with(binding.pm10VP2){
            offscreenPageLimit= NUM3.toInt()
            setPageTransformer { page, position ->
                pageTransformer(page,position)
            }
            adapter=imagePm10Adapter
        }
    }

    private fun pageTransformer(page: View, position: Float) {
        val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.IndexOffsetBetweenPages).toFloat()
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