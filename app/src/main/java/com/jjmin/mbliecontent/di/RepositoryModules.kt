package com.jjmin.mbliecontent.di

import com.jjmin.mbliecontent.data.remote.LoginRepository
import com.jjmin.mbliecontent.data.remote.LoginRepositoryImpl
import com.jjmin.mbliecontent.data.remote.RegisterRepository
import com.jjmin.mbliecontent.data.remote.RegisterRepositoryImpl
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

    val repositotyModule = listOf(loginmodule)
}