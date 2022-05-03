package com.sjchoi.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sjchoi.weather.common.PJRepository
import com.sjchoi.weather.viewmodel.WeatherViewModel

class WeatherViewModelFactory(private val repository: PJRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}