package com.jjmin.mbliecontent.ui.splash

import android.os.Bundle
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivitySplashBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import org.koin.core.parameter.parametersOf
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val LayoutId = R.layout.activity_splash

    val useCase by lazy { SplashUseCase(this) }
    val viewmodel : SplashViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewmodel
        viewmodel.start()
    }
}
