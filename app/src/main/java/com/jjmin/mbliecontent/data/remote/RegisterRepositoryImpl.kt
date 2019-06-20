package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import io.reactivex.Single

class RegisterRepositoryImpl (val api : NetworkApi) : RegisterRepository{
    override fun Resister(id: String, password: String,email: String, phone: String, name: String): Single<BaseData> {
        return api.Register(id,password,email,phone,name).map { it }
    }
}