package com.jjmin.mbliecontent

import android.app.Application
import android.content.Context
import com.jjmin.mbliecontent.di.Modules
import com.jjmin.mbliecontent.di.NetworkModules
import com.jjmin.mbliecontent.di.RepositoryModules
import com.jjmin.mbliecontent.util.ORMUtils
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    var context : Context? = null


    override fun onCreate() {
        super.onCreate()

        startKoin {
            this@MyApplication
            modules(
                Modules.LoginModule,
                Modules.splashModule,
                Modules.MainModule,
                Modules.RegisterModule,
                NetworkModules.networkModules,
                RepositoryModules.loginmodule
            )
        }

        context = this
        ORMUtils.setORM(this)
    }
}