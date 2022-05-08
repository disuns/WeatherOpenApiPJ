package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.databinding.AirRecyclerviewItemBinding

class AirRecyclerViewAdapter(private val adapterItem:List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class AirRecyclerViewHolder(val binding: AirRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = AirRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AirRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as AirRecyclerViewHolder).binding){
            viewTVItem.text = adapterItem[position]
        }
    }

    override fun getItemCount() = adapterItem.size

}