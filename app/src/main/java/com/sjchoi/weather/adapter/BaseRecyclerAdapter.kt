package com.sjchoi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseRecyclerAdapter<VB : ViewBinding>(private val inflate:Inflate<VB>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class BaseViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = inflate.invoke(LayoutInflater.from(parent.context),parent,false)

        return BaseViewHolder(binding)
    }
}