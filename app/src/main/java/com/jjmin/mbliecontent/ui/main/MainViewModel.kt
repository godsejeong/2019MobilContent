package com.jjmin.mbliecontent.ui.main

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.ShapeDialog
import com.jjmin.mbliecontent.util.SingleLiveEvent

class MainViewModel(val useCase: MainUseCase) : ViewModel(){

    val _clickNext = SingleLiveEvent<Any>()
    val clickNext : LiveData<Any> get() = _clickNext

    val FabClick = View.OnClickListener {
        SetIntnet(ShapeDialog::class.java,100)
    }

//    val TvClick = View.OnClickListener {
//
//    }

    fun NextClick(){
        _clickNext.call()
    }

    fun SendServer(){

    }

    fun SetIntnet(activity : Class<*>,requestCode : Int){
        var intnet = Intent(useCase.activity,activity)
        useCase.activity.startActivityForResult(intnet,requestCode)
    }


}