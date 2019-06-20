package com.jjmin.mbliecontent.data.remote

import com.jjmin.mbliecontent.data.model.LoginData
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkApi {

    @POST("/auth/signin")
    fun Login(@Query("id") id: String, @Query("passwd") passwd: String): Single<LoginData>

//    @POST("/")
//    fun Login(@Field("id") id: String, @Field("passwd") passwd: String): Single<LoginData>
}