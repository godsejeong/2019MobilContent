package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.SendShapeData
import io.reactivex.Single

interface MainRepository{
    fun SendShape(list : List<SendShapeData>,id : String) : Single<BaseData>
}