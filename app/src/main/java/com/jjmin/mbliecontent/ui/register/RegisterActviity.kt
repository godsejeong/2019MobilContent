package com.jjmin.mbliecontent.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityLoginBusinessBinding
import com.jjmin.mbliecontent.databinding.ActivityRegisterActviityBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.login.LoginUseCase
import com.jjmin.mbliecontent.ui.login.LoginViewModel
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterActviity : BaseActivity<ActivityRegisterActviityBinding>() {
    override val LayoutId = R.layout.activity_register_actviity

    val useCase by lazy { RegisterUseCase(this) }
    val viewmodel : RegisterViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

        viewmodel.clickresister.observe(this, Observer {

            var id = viewDataBinding.registerIdEt.text.toString()
            var password = viewDataBinding.registerPasswordEt.text.toString()
            var name = viewDataBinding.registerCompanyNameEt.text.toString()
            var email = viewDataBinding.registerCompanyEmailEt.text.toString()
            var phone = viewDataBinding.registerCompanyPhoneEt.text.toString()

            if(id.isNotEmpty() || password.isNotEmpty() || name.isNotEmpty() || email.isNotEmpty() || phone.isNotEmpty()) {
                viewmodel.register(id, password, email, phone, name)
            }
            else
                toast("모든 정보를 기입해주세요.")
        })

    }
}
