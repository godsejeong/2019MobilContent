package com.jjmin.mbliecontent

import android.app.Application
import android.content.Context
import com.jjmin.mbliecontent.di.Modules
import com.jjmin.mbliecontent.di.NetworkModules
import com.jjmin.mbliecontent.di.RepositoryModules
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SharedUtils
import org.koin.core.context.startKoin

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
                Modules.foodModule,
                NetworkModules.networkModules,
                RepositoryModules.loginmodule,
                RepositoryModules.registermodule,
                RepositoryModules.shapemodule
            )
        }

        context = this
        SharedUtils.init(this)
    }
}