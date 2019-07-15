package com.jjmin.mbliecontent.adapter

import android.opengl.Visibility
import android.view.View
import androidx.databinding.BindingAdapter

object BindAdapter {

    @JvmStatic
    @BindingAdapter(value = ["visible"])
    fun SetVisible(view : View,isvisible : Boolean){
        if(isvisible)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}