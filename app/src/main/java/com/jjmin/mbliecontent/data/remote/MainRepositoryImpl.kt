package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.SendSpapeData
import io.reactivex.Single

class MainRepositoryImpl(var api: NetworkApi) : MainRepository{
    override fun SendShape(list: List<SendSpapeData>): Single<BaseData> {
        return api.SendShape(list).map { it }
    }

}