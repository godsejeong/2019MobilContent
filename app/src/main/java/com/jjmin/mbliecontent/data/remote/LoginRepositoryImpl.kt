package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.ArrangementData
import com.jjmin.mbliecontent.data.model.LoginData
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.data.model.UserLoginData
import io.reactivex.Single

class LoginRepositoryImpl(val api : NetworkApi) : LoginRepository{

    override fun UserLogin(id: String) : Single<UserLoginData> {
        return api.UserLogin(id).map { it }
    }

    override fun Login(id: String, passwd: String): Single<LoginData> {
        return api.Login(id,passwd).map { it }
    }
}