package com.jjmin.mbliecontent.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityLoginBusinessBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BusinessLoginActivity : BaseActivity<ActivityLoginBusinessBinding>() {
    override val LayoutId = R.layout.activity_login_business

    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

        viewmodel.clicklogin.observe(this, Observer {
            var id = viewDataBinding.loginIdEt.text.toString()
            var password = viewDataBinding.loginPwEt.text.toString()
            if (id.isNotEmpty() || password.isNotEmpty())
                viewmodel.Login(viewDataBinding.loginIdEt.text.toString(),viewDataBinding.loginPwEt.text.toString())
            else
                toast("아이디나 비밀번호를 입력해주세요")
        })
    }
}
