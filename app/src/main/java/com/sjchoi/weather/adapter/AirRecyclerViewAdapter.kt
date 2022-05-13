package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjchoi.weather.databinding.AirRecyclerviewItemBinding

class AirRecyclerViewAdapter(private val adapterItem:List<String>) : BaseRecyclerAdapter<AirRecyclerviewItemBinding>(AirRecyclerviewItemBinding::inflate){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with((holder as BaseRecyclerAdapter<AirRecyclerviewItemBinding>.BaseViewHolder).binding){
            viewTVItem.text = adapterItem[position]
        }
    }

    override fun getItemCount() = adapterItem.size

}