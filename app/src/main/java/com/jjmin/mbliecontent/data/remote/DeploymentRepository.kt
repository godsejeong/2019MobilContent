package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.DeploymentData
import io.reactivex.Single

interface DeploymentRepository {
    fun ShapeDeployment(id : String) : Single<DeploymentData>
}