package com.jjmin.mbliecontent

import android.app.Application
import android.content.Context
import com.jjmin.mbliecontent.di.Modules
import com.jjmin.mbliecontent.di.NetworkModules
import com.jjmin.mbliecontent.di.RepositoryModules
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SharedUtils
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyApplication : Application() {

    var context : Context? = null


    override fun onCreate() {
        super.onCreate()

        startKoin {
            this@MyApplication

            modules(
                Modules.LoginModule,
                Modules.SplashModule,
                Modules.MainModule,
                Modules.RegisterModule,
                Modules.FoodModule,
                Modules.DeploymentModule,
                NetworkModules.networkModules,
                RepositoryModules.LoginModule,
                RepositoryModules.RegisterModule,
                RepositoryModules.MainModule,
                RepositoryModules.ShapeDeploymentmodule
                )
        }

        context = this
        SharedUtils.init(this)
    }
}