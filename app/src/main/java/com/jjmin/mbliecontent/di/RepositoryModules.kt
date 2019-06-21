package com.jjmin.mbliecontent.di

import com.jjmin.mbliecontent.data.remote.*
import org.koin.dsl.module

object RepositoryModules{

    val loginmodule = module {
        single {
            LoginRepositoryImpl(get()) as LoginRepository
        }
    }

    val registermodule = module {
        single {
            RegisterRepositoryImpl(get()) as RegisterRepository
        }
    }

    val shapemodule = module {
        single {
            MainRepositoryImpl(get()) as MainRepository
        }
    }

    val repositotyModule = listOf(loginmodule)
}