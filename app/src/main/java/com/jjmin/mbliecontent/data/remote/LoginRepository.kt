package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.LoginData
import io.reactivex.Single

interface LoginRepository {
    fun Login(id : String,passwd : String) : Single<LoginData>
}