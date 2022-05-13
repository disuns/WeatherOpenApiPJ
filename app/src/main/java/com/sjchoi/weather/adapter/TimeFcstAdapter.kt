package com.sjchoi.weather.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.WeatherTimeItemBinding
import com.sjchoi.weather.dataclass.datapotal.fcstdata.TimeFcstData

class TimeFcstAdapter(private val adapterItem : List<TimeFcstData>) : BaseRecyclerAdapter<WeatherTimeItemBinding>(WeatherTimeItemBinding::inflate){

    private val dataConvert = DataConvert.getDataConvert()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as BaseRecyclerAdapter<WeatherTimeItemBinding>.BaseViewHolder).binding){
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

    override fun getItemCount() = adapterItem.size

}