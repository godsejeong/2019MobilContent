package com.jjmin.mbliecontent.ui.deployment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.databinding.ActivityShapeDeploymentBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.main.MainUseCase
import com.jjmin.mbliecontent.util.RealmUtils.realm
import com.jjmin.mbliecontent.util.SharedUtils
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ShapeDeploymentActivity : BaseActivity<ActivityShapeDeploymentBinding>() {
    override val LayoutId: Int = R.layout.activity_shape_deployment

    val useCase by lazy { DeploymentUseCase(this) }
    val viewmodel: DeploymentViewmodel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewmodel



    }
}
