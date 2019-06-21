package com.jjmin.mbliecontent.ui.main

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jjmin.mbliecontent.ShapeDialog
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.data.remote.MainRepository
import com.jjmin.mbliecontent.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class MainViewModel(val useCase: MainUseCase,var mainRepository: MainRepository) : ViewModel(){

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

    fun SendServer(list : List<SendShapeData>,id : String){
        Log.e("serverList", Gson().toJson(list))
        mainRepository.SendShape(list,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                useCase.activity.toast("정상적으로 서버에 반영되었습니다.")
                Log.e("success", it.success.toString())
            }){
                useCase.activity.toast("서버가 점검중입니다.")
                Log.e("MainErrorMessage",it.message)
            }

    }

    fun SetIntnet(activity : Class<*>,requestCode : Int){
        var intnet = Intent(useCase.activity,activity)
        useCase.activity.startActivityForResult(intnet,requestCode)
    }


}