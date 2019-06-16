package com.jjmin.mbliecontent

import android.app.Application
import android.content.Context
import com.jjmin.mbliecontent.di.Modules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyApplication : Application() {

    var context : Context? = null

    override fun onCreate() {
        super.onCreate()

        startKoin {
            this@MyApplication
            modules (
                Modules.splashModule,
                Modules.MainModule,
                Modules.RegisterModule,
                Modules.LoginModule
            )
        }

        context = this
    }
}