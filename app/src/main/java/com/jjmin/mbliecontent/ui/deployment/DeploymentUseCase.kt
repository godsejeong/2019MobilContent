package com.jjmin.mbliecontent.ui.deployment

import android.app.Activity
import android.os.Bundle
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.ui.base.BaseActivity

class DeploymentUseCase (val activity : ShapeDeploymentActivity){
    var bundle = activity.intent.extras

    var list = bundle.getSerializable("list") as ArrayList<SendShapeData>

}