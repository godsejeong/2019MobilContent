package com.jjmin.mbliecontent.ui.blind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityBlindUserBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.util.TTSUtils
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BlindUserActivity : BaseActivity<ActivityBlindUserBinding>() {
    var tts = TTSUtils

    override val LayoutId: Int = R.layout.activity_blind_user

    val useCase by lazy { BlindUseCase(this) }
    val viewmodel : BlindViewModel by viewModel { parametersOf(useCase) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            tts.stop()
            overridePendingTransition(0, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        overridePendingTransition(0, 0)

    }
}
