package com.jjmin.mbliecontent.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jjmin.mbliecontent.CustomDrawView
import com.jjmin.mbliecontent.util.SharedUtils

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(){
    lateinit var viewDataBinding: T
    abstract val LayoutId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this,LayoutId)
        viewDataBinding.lifecycleOwner = this
    }


}