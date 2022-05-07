package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sjchoi.weather.common.DataConvert
import com.sjchoi.weather.common.WeatherApplication
import com.sjchoi.weather.viewmodel.WeatherViewModel

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate:Inflate<VB>) : Fragment() {
    private var _binding : VB? = null
    val binding get()= _binding!!

    lateinit var viewModel : WeatherViewModel
    val dataConvert = DataConvert.getDataConvert()
    val weatherApplication = WeatherApplication.getWeatherApplication()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}