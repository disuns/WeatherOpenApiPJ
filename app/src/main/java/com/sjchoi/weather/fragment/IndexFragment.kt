package com.sjchoi.weather.fragment

import android.os.Bundle
import android.view.View
import com.sjchoi.weather.databinding.FragmentIndexBinding

class IndexFragment : BaseFragment<FragmentIndexBinding>(FragmentIndexBinding::inflate) {

    companion object {
        fun newInstance() = IndexFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}