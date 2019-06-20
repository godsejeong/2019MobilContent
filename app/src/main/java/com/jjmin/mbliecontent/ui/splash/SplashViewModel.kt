package com.jjmin.mbliecontent.ui.splash

import android.content.Intent
import android.os.Handler
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.ui.login.BusinessLoginActivity
import com.jjmin.mbliecontent.ui.login.SelectLoginActivity

class SplashViewModel(val useCase: SplashUseCase) : ViewModel() {

    fun start() {
        val handler = Handler()
        handler.postDelayed({
            var intent: Intent = Intent(useCase.activity.application,SelectLoginActivity::class.java)
            useCase.activity.startActivity(intent)
            useCase.activity.finish()
        }, 3000)
    }
}