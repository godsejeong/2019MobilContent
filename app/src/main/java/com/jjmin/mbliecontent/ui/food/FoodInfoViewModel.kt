package com.jjmin.mbliecontent.ui.food

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.ui.main.MainUseCase
import com.jjmin.mbliecontent.util.SingleLiveEvent

class FoodInfoViewModel(val useCase: FoodInfoUseCase) : ViewModel() {

    val _clickComplete = SingleLiveEvent<Any>()
    val clickComplete : LiveData<Any> get() = _clickComplete

    val BackButton =  View.OnClickListener {
        useCase.activity.finish()
    }

    fun CompleteClick(){
        _clickComplete.call()
    }

}