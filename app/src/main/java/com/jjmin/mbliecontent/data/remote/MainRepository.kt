package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.SendSpapeData
import io.reactivex.Single

interface MainRepository{
    fun SendShape(list : List<SendSpapeData>) : Single<BaseData>
}