package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.DeploymentData
import io.reactivex.Single

class DeploymentRepositoryImpl(val api : NetworkApi) : DeploymentRepository{
    override fun ShapeDeployment(id: String): Single<DeploymentData> {
        return api.ShapeDeploment(id).map { it }
    }
}