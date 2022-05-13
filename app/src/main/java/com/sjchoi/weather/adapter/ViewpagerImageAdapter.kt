package com.sjchoi.weather.adapter

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sjchoi.weather.databinding.AirQualityImageItemBinding

class ViewpagerImageAdapter(private val parentFragment: Fragment, private val adapterItem : List<String>) : BaseRecyclerAdapter<AirQualityImageItemBinding>(AirQualityImageItemBinding::inflate){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as BaseRecyclerAdapter<AirQualityImageItemBinding>.BaseViewHolder).binding){
            Glide.with(parentFragment).load(adapterItem[position]).into(airQualityItemIv)
        }
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }
}