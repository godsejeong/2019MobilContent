package com.jjmin.mbliecontent.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.eclipsesource.json.Json
import com.google.gson.JsonObject
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityLoginBusinessBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.toast
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class BusinessLoginActivity : BaseActivity<ActivityLoginBusinessBinding>() {
    override val LayoutId = R.layout.activity_login_business
    var loginObj = JSONObject()
    var loginsubject: PublishSubject<JSONObject> = PublishSubject.create()
    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

        viewmodel.clicklogin.observe(this, Observer {
            loginObj.put("id",viewDataBinding.loginIdEt.text.toString())
            loginObj.put("password",viewDataBinding.loginPwEt.text.toString())
            loginsubject.onNext(loginObj)
        })

        loginsubject
            .debounce (500,TimeUnit.MILLISECONDS)
            .subscribe {
                var id = it.get("id") as String
                var password = it.get("password") as String
                if (id.isNotEmpty() || password.isNotEmpty())
                    viewmodel.Login(viewDataBinding.loginIdEt.text.toString(),viewDataBinding.loginPwEt.text.toString())
                else
                    toast("아이디나 비밀번호를 입력해주세요")
            }
    }
}
