package com.jjmin.mbliecontent.di

import com.jjmin.mbliecontent.ui.food.FoodInfoUseCase
import com.jjmin.mbliecontent.ui.food.FoodInfoViewModel
import com.jjmin.mbliecontent.ui.login.LoginUseCase
import com.jjmin.mbliecontent.ui.login.LoginViewModel
import com.jjmin.mbliecontent.ui.main.MainUseCase
import com.jjmin.mbliecontent.ui.main.MainViewModel
import com.jjmin.mbliecontent.ui.register.RegisterUseCase
import com.jjmin.mbliecontent.ui.register.RegisterViewModel
import com.jjmin.mbliecontent.ui.splash.SplashUseCase
import com.jjmin.mbliecontent.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules{
    val splashModule = module {
        viewModel {(usecase : SplashUseCase) ->
            SplashViewModel(usecase)
        }
    }

    val MainModule = module {
        viewModel {(usecase : MainUseCase) ->
            MainViewModel(usecase,get())
        }
    }

    val LoginModule = module {
        viewModel {(usecase : LoginUseCase) ->
            LoginViewModel(usecase,get())
        }
    }

    val RegisterModule = module {
        viewModel {(usecase : RegisterUseCase) ->
            RegisterViewModel(usecase,get())
        }
    }

    val foodModule = module {
        viewModel { (usecase : FoodInfoUseCase)->
            FoodInfoViewModel(usecase)
        }
    }

    val uiModule = listOf(splashModule,MainModule,LoginModule,RegisterModule)
}