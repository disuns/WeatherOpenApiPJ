package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sjchoi.weather.databinding.AirQualityImageItemBinding

class ViepagerImageAdapter(private val parentFragment: Fragment,private val adapterItem : List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class ImageViewHolder(val binding: AirQualityImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = AirQualityImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as ImageViewHolder).binding){
            Glide.with(parentFragment).load(adapterItem[position]).into(airQualityItemIv)
        }
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }
}