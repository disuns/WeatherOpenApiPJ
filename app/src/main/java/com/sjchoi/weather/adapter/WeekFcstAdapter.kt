package com.sjchoi.weather.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.WeatherWeekRecyclerItemBinding
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekFcstData

class WeekFcstAdapter (private val adapterItem : MutableList<WeekFcstData>) : BaseRecyclerAdapter<WeatherWeekRecyclerItemBinding>(WeatherWeekRecyclerItemBinding::inflate){

    private val dataConvert = DataConvert.getDataConvert()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as BaseRecyclerAdapter<WeatherWeekRecyclerItemBinding>.BaseViewHolder).binding){
            with(dataConvert){
                with(adapterItem[position]){
                    weekDateTV.text = weekDateConvert(weekDate)
                    weekAmRainperTV.text=rainPerConvert(rainAm)
                    weekPmRainperTV.text=rainPerConvert(rainPm)
                    weekAmSkyTV.text=skyAm
                    weekPmSkyTV.text=skyPm
                }
            }
        }
    }

    override fun getItemCount() = adapterItem.size

}