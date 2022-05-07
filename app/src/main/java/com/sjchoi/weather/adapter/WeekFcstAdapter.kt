package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.WeatherWeekRecyclerItemBinding
import com.sjchoi.weather.dataclass.datapotal.fcstdata.WeekFcstData

class WeekFcstAdapter (private val adapterItem : MutableList<WeekFcstData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val dataConvert = DataConvert.getDataConvert()
    inner class WeekFcstViewHolder(val binding: WeatherWeekRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = WeatherWeekRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekFcstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as WeekFcstViewHolder).binding){
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

    override fun getItemCount(): Int {
        return adapterItem.size
    }
}