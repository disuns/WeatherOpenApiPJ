package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.WeatherTimeItemBinding
import com.sjchoi.weather.dataclass.datapotal.fcstdata.TimeFcstData

class TimeFcstAdapter(private val adapterItem : List<TimeFcstData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val dataConvert = DataConvert.getDataConvert()

    inner class TimeFcstViewHolder(val binding: WeatherTimeItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = WeatherTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeFcstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as TimeFcstViewHolder).binding){
            with(dataConvert){
                with(adapterItem[position]){
                    itemTimeTV.text = timeDataConvert(fcstTime)
                    itemDateTV.text = dateConvert(fcstDate)
                    var fcstImg = fcstRainImgConvert(rain)
                    fcstImg = skyImgEnum(sky,fcstImg)
                    itemWeatherIV.setImageDrawable(fcstImgConvert(fcstImg))
                    itemRainMmTV.text =rainConvert(rainMm)
                    itemRainTV.text =rainPerConvert(rainPer)
                    itemTempTV.text = tempConvert(temp)
                    itemWetTV.text = wetConvert(wet)
                    itemWindTV1.text = windDir(windDir)
                    itemWindTV2.text = windPower(windPower)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }
}