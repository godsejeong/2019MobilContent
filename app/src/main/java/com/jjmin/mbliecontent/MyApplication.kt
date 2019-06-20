package com.jjmin.mbliecontent

import android.app.Application
import android.content.Context
import com.jjmin.mbliecontent.di.Modules
import com.jjmin.mbliecontent.di.NetworkModules
import com.jjmin.mbliecontent.di.RepositoryModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyApplication : Application() {

    var context : Context? = null

    val appModule = listOf(
        NetworkModules.networkModules,
        RepositoryModules.repositotyModule,
        Modules.uiModule)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            this@MyApplication
            appModule
        }

        context = this
    }
}