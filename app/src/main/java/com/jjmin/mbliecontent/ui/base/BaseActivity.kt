package com.jjmin.mbliecontent.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SharedUtils
import io.realm.Realm

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(){
    lateinit var viewDataBinding: T
    abstract val LayoutId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(applicationContext)

        viewDataBinding = DataBindingUtil.setContentView(this,LayoutId)
        viewDataBinding.lifecycleOwner = this
    }
}