package com.jjmin.mbliecontent.ui.register

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.data.remote.RegisterRepository
import com.jjmin.mbliecontent.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class RegisterViewModel(val useCase: RegisterUseCase,val registerRepository : RegisterRepository) : ViewModel(){

    val _clickresister = SingleLiveEvent<Any>()
    val clickresister : LiveData<Any> get() = _clickresister

    val BackButton =  View.OnClickListener {
        useCase.actviity.finish()
    }

    fun registerClick(){
        _clickresister.call()
    }

    fun register(id : String,password : String,email : String,phone : String,name : String){
        Log.e("test", "$id     $password      $name     $email      $phone")
//
        registerRepository.Resister(id,password,email,phone,name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(it.success.toString(),it.message)
                useCase.actviity.toast("회원가입 완료되었습니다.")
                useCase.actviity.finish()
            })
            {
            Log.e("RegisterError",it.message)
            }
    }
}