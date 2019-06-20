package com.jjmin.mbliecontent.ui.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jjmin.mbliecontent.data.model.LoginData
import com.jjmin.mbliecontent.data.remote.LoginRepository
import com.jjmin.mbliecontent.ui.register.RegisterActviity
import com.jjmin.mbliecontent.ui.login.BusinessLoginActivity
import com.jjmin.mbliecontent.util.ORMUtils
import com.jjmin.mbliecontent.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class LoginViewModel(val useCase: LoginUseCase,val loginRepository: LoginRepository) : ViewModel(){

    val _loginID = MutableLiveData<String>()
    val loginID : LiveData<String> get() = _loginID

    val _loginPasswd = MutableLiveData<String>()
    val loginPasswd : LiveData<String> get() = _loginPasswd

    val _clicklogin = SingleLiveEvent<Any>()
    val clicklogin : LiveData<Any> get() = _clicklogin

    val UserButton = View.OnClickListener {
        SetIntnet(UserLoginActivity::class.java)
    }

    val BusinessButton = View.OnClickListener {
        SetIntnet(BusinessLoginActivity::class.java)
    }

    val RegisterButton = View.OnClickListener {
        SetIntnet(RegisterActviity::class.java)
    }

    val BackButton =  View.OnClickListener {
        useCase.actviity.finish()
    }

    fun LoginClick(){
        _clicklogin.call()
    }

    fun Login(id : String , passwd : String){
        Log.e("asdf","$id  $passwd")

        loginRepository.Login(id,passwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                ORMUtils.UserDB().save(it)
                useCase.actviity.toast("로그인 완료")
            }) {
                Log.e("loginError",it.message)
                if(it.message?.contains("401")!!){
                    useCase.actviity.toast("이이디나 비밀번호가 일치하지 않습니다.")
                }else{
                    useCase.actviity.toast("서버를 점검중입니다. 잠시후에 시도해주세요")
                }
            }
    }

    fun SetIntnet(activity : Class<*>){
        var intnet = Intent(useCase.actviity,activity)
        useCase.actviity.startActivity(intnet)
    }
}