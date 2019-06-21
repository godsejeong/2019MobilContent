package com.jjmin.mbliecontent.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.ui.base.BaseActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserLoginActivity : BaseActivity<com.jjmin.mbliecontent.databinding.ActivityUserLoginBinding>() {
    override val LayoutId = R.layout.activity_user_login

    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

        viewmodel.userClicklogin.observe(this, Observer {
            var id = viewDataBinding.userLoginIdEt.text.toString()

            if(id.isNotEmpty())
                viewmodel.UserLogin(id)
            else
                toast("아이디를 입력해해세요")
        })

    }
}
