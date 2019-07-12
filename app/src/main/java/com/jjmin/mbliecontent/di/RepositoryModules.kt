package com.jjmin.mbliecontent.di

import com.jjmin.mbliecontent.data.remote.*
import com.jjmin.mbliecontent.ui.deployment.ShapeDeploymentActivity
import org.koin.dsl.module

object RepositoryModules{

    val LoginModule = module {
        single {
            LoginRepositoryImpl(get()) as LoginRepository
        }
    }

    val RegisterModule = module {
        single {
            RegisterRepositoryImpl(get()) as RegisterRepository
        }
    }

    val MainModule = module {
        single {
            MainRepositoryImpl(get()) as MainRepository
        }
    }

    val ShapeDeploymentmodule = module {
        single {
            DeploymentRepositoryImpl(get()) as DeploymentRepository
        }
    }
}