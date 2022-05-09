package com.sjchoi.weather.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.sjchoi.weather.R

class ProgressDialog(private val context:Context) {

    private lateinit var dialog : Dialog

    fun showDialog() {
        dialog = Dialog(context)
        with(dialog){
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.progress_dialog)

            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
          //  window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // dialog의 dim 처리 배경 제거
            window?.setDimAmount(0.4f)

            setCancelable(false)

            show()
        }
    }

    fun closeDialog(){
        if(dialog.isShowing)
            dialog.dismiss()
    }
}