package com.jjmin.mbliecontent.ui.splash

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.ui.login.SelectLoginActivity
import com.jjmin.mbliecontent.ui.main.MainActivity
import com.jjmin.mbliecontent.util.RealmUtils
import org.jetbrains.anko.toast

class SplashViewModel(val useCase: SplashUseCase) : ViewModel() {
    var intent: Intent? = null
    fun start() {
        val handler = Handler()

        handler.postDelayed({
            if(RealmUtils.getToken()!= null && RealmUtils.getToken() != ""){
                intent = Intent(useCase.activity.application,MainActivity::class.java)
                useCase.activity.toast("${RealmUtils.getCompanyName()} 로그인 합니다.")
            }else if(RealmUtils.getToken() == null || RealmUtils.getToken() == "")
             intent = Intent(useCase.activity.application,SelectLoginActivity::class.java)

            useCase.activity.startActivity(intent)
            useCase.activity.finish()
        }, 3000)
    }
}