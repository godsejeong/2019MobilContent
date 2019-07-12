package com.jjmin.mbliecontent.ui.deployment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jjmin.mbliecontent.data.remote.DeploymentRepository
import com.jjmin.mbliecontent.util.RealmUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

class DeploymentViewmodel(val useCase: DeploymentUseCase,val repository: DeploymentRepository) : ViewModel(){

    init {
        ShapeDeployment()
    }

    fun ShapeDeployment(){
        repository.ShapeDeployment(RealmUtils.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                Log.e("deploment", Gson().toJson(it))
            }){
                useCase.activity.toast("서버가 점검중입니다.")
                Log.e("DeploymentErrorMessage",it.message)
            }
    }

}



강석문병신새끼강ㅅㄱ문병신새끼강석문병신새끼강석문병신새끼강석nsqudtlstoRlrkdtjranansq병신새끼강석문병신새끼강석ㄱ문병신새끼갇석문병신새끼강석문병신새끼ㅏㅇ석문병신새끼강석강석문병신새끼강석문병신새기강석문병강석뭉볏