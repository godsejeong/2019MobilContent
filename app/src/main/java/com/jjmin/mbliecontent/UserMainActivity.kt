package com.jjmin.mbliecontent

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.jjmin.mbliecontent.databinding.ActivityUserMainBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.login.LoginUseCase
import com.jjmin.mbliecontent.ui.login.LoginViewModel
import com.jjmin.mbliecontent.ui.sticker.Sticker
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserMainActivity : BaseActivity<ActivityUserMainBinding>() {
    override val LayoutId: Int = R.layout.activity_user_main

    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewmodel
    }
}
