package com.sjchoi.weather

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sjchoi.weather.common.PJRepository
import com.sjchoi.weather.common.ProgressDialog
import com.sjchoi.weather.viewmodel.WeatherViewModel

class WeatherViewModelFactory(private val repository: PJRepository, private val ornerActivity: Activity, private val progressDialog: ProgressDialog): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository,ornerActivity,progressDialog) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}