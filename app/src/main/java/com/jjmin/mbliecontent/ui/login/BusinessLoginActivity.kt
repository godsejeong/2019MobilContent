package com.jjmin.mbliecontent.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BusinessLoginActivity : BaseActivity<com.jjmin.mbliecontent.databinding.ActivityLoginBusinessBinding>() {
    override val LayoutId = R.layout.activity_login_business

    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel
    }
}
