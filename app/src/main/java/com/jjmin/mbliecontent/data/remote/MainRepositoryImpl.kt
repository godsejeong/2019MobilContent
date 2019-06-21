package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.SendShapeData
import io.reactivex.Single

class MainRepositoryImpl(var api: NetworkApi) : MainRepository{
    override fun SendShape(list: List<SendShapeData>): Single<BaseData> {
        return api.SendShape(list).map { it }
    }

}