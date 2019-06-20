package com.jjmin.mbliecontent.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserLoginActivity : BaseActivity<com.jjmin.mbliecontent.databinding.ActivityUserLoginBinding>() {
    override val LayoutId = R.layout.activity_user_login

    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

    }
}
