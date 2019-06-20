package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.LoginData
import io.reactivex.Single
import retrofit2.http.*

interface NetworkApi {

    @FormUrlEncoded
    @POST("/auth/signin")
    fun Login(@Field("id") id: String, @Field("passwd") passwd: String): Single<LoginData>

    @FormUrlEncoded
    @POST("/auth/signup")
    fun Register(
        @Field("id") id: String,
        @Field("passwd") passwd: String,
        @Field("email") email: String,
        @Field("pn") pn: String,
        @Field("name") name: String
    ): Single<BaseData>
}