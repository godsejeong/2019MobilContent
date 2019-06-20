package com.jjmin.mbliecontent.ui.login

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.ui.register.RegisterActviity
import com.jjmin.mbliecontent.ui.login.BusinessLoginActivity

class LoginViewModel(val useCase: LoginUseCase) : ViewModel(){

    val UserClick = View.OnClickListener {
        SetIntnet(UserLoginActivity::class.java)
    }

    val BusinessClick = View.OnClickListener {
        SetIntnet(BusinessLoginActivity::class.java)
    }

    val RegisterClick = View.OnClickListener {
        SetIntnet(RegisterActviity::class.java)
    }

    val BackClick =  View.OnClickListener {
        useCase.actviity.finish()
    }

    fun SetIntnet(activity : Class<*>){
        var intnet = Intent(useCase.actviity,activity)
        useCase.actviity.startActivity(intnet)
    }
}