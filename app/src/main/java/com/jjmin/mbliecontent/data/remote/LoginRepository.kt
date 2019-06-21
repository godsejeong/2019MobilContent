package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.LoginData
import com.jjmin.mbliecontent.data.model.UserLoginData
import io.reactivex.Single

interface LoginRepository {
    fun Login(id : String,passwd : String) : Single<LoginData>
    fun UserLogin(id : String) : Single<UserLoginData>
}