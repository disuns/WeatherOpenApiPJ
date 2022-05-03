package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.databinding.WeatherWeekRecyclerItemBinding
import com.sjchoi.weather.dataclass.fcstdata.WeekFcstData

class WeekFcstAdapter (private val adapterItem : MutableList<WeekFcstData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class WeekFcstViewHolder(val binding: WeatherWeekRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = WeatherWeekRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekFcstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as WeekFcstViewHolder).binding){
            weekDateTV.text = DataConvert.getDataConvert().weekDateConvert(adapterItem[position].weekDate)
            weekAmRainperTV.text=DataConvert.getDataConvert().rainPerConvert(adapterItem[position].rainAm)
            weekPmRainperTV.text=DataConvert.getDataConvert().rainPerConvert(adapterItem[position].rainPm)
            weekAmSkyTV.text=adapterItem[position].skyAm
            weekPmSkyTV.text=adapterItem[position].skyPm
        }
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }
}