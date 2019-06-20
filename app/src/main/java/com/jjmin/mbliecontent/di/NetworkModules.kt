package com.jjmin.mbliecontent.di

import com.jjmin.mbliecontent.data.remote.NetworkApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModules{
    var url = "http://ec2-52-68-72-218.ap-northeast-1.compute.amazonaws.com:3000"

    val networkModules = module {
        single {
            Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()
                .create(NetworkApi::class.java)
        }
    }
}