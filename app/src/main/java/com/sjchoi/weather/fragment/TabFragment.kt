package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sjchoi.weather.databinding.FragmentTabBinding

class TabFragment : BaseFragment<FragmentTabBinding>(FragmentTabBinding::inflate) {
    companion object{
        fun newInstance() = TabFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}