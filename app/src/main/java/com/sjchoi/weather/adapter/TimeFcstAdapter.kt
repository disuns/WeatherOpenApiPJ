package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.WeatherTimeItemBinding
import com.sjchoi.weather.dataclass.fcstdata.TimeFcstData

class TimeFcstAdapter(private val adapterItem : List<TimeFcstData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class TimeFcstViewHolder(val binding: WeatherTimeItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = WeatherTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeFcstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as TimeFcstViewHolder).binding){
            itemTimeTV.text = DataConvert.getDataConvert().timeDataConvert(adapterItem[position].fcstTime)
            itemDateTV.text = DataConvert.getDataConvert().dateConvert(adapterItem[position].fcstDate)
            var fcstImg = DataConvert.getDataConvert().fcstRainImgConvert(adapterItem[position].rain)
            fcstImg = DataConvert.getDataConvert().skyImgEnum(adapterItem[position].sky,fcstImg)
            itemWeatherIV.setImageDrawable(DataConvert.getDataConvert().fcstImgConvert(fcstImg))
            itemRainMmTV.text =DataConvert.getDataConvert().rainConvert(adapterItem[position].rainMm)
            itemRainTV.text =DataConvert.getDataConvert().rainPerConvert(adapterItem[position].rainPer)
            itemTempTV.text = DataConvert.getDataConvert().tempConvert(adapterItem[position].temp)
            itemWetTV.text = DataConvert.getDataConvert().wetConvert(adapterItem[position].wet)
            itemWindTV1.text = DataConvert.getDataConvert().windDir(adapterItem[position].windDir)
            itemWindTV2.text = DataConvert.getDataConvert().windPower(adapterItem[position].windPower)
        }
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }
}