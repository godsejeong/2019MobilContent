package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import io.reactivex.Single

interface RegisterRepository {
    fun Resister(id : String,password : String,email : String,phone : String,name : String) : Single<BaseData>
}