package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.R
import com.sjchoi.weather.data.FcstData
import com.sjchoi.weather.databinding.WeatherTimeRecyclerItemBinding

class TimeFcstAdapter(private val adapterItem : FcstData) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class TimeFcstViewHolder(val binding: WeatherTimeRecyclerItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = WeatherTimeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeFcstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //(holder as TimeFcstViewHolder).binding.
    }

    override fun getItemCount(): Int {
        return adapterItem.response.body.items.item.size
    }
}