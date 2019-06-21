package com.jjmin.mbliecontent.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.UserMainActivity
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.data.remote.LoginRepository
import com.jjmin.mbliecontent.ui.main.MainActivity
import com.jjmin.mbliecontent.ui.register.RegisterActviity
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.jetbrains.anko.toast
import java.util.*

class LoginViewModel(val useCase: LoginUseCase,val loginRepository: LoginRepository) : ViewModel(){

    init {
        Realm.init(useCase.activity.applicationContext)
    }

    val _clicklogin = SingleLiveEvent<Any>()
    val clicklogin : LiveData<Any> get() = _clicklogin

    val _userClicklogin = SingleLiveEvent<Any>()
    val userClicklogin : LiveData<Any> get() = _userClicklogin

    val UserButton = View.OnClickListener {
        SetIntnet(UserLoginActivity::class.java)
    }

    val BusinessButton = View.OnClickListener {
        SetIntnet(BusinessLoginActivity::class.java)
    }

    val RegisterButton = View.OnClickListener {
        SetIntnet(RegisterActviity::class.java)
    }

    fun LoginClick(){
        _clicklogin.call()
    }

    @SuppressLint("CheckResult")
    fun Login(id : String, passwd : String){
        Log.e("asdf","$id  $passwd")

        loginRepository.Login(id,passwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var mRealm = Realm.getDefaultInstance()
                mRealm.beginTransaction()
                Log.e("asdasdf", it.success.toString())
                var userdata : UserInfo = mRealm.createObject(UserInfo::class.java,UUID.randomUUID().toString())
                var user = mRealm.copyFromRealm(userdata)

//                useCase.activity.toast("로그인 완료 되었습니다.")
                user.apply {
                    this.id = it.user?.id!!
                    this.passwd = it.user?.passwd!!
                    this.companyName = it.user?.companyName!!
                    this.phoneNumber = it.user?.phoneNumber!!
                    this.companyEmail = it.user?.companyEmail!!
                }

//                try {
//                mRealm.copyToRealm(user)
                mRealm.commitTransaction()
                useCase.activity.toast("${RealmUtils.getCompanyName()} 로그인 합니다.")
                useCase.activity.finishAffinity()
                SetIntnet(MainActivity::class.java)

//                }catch (e : RealmPrimaryKeyConstraintException){
//                    SetIntnet(MainActivity::class.java)
//                }

            }) {
                Log.e("loginError",it.message)
                if(it.message?.contains("401")!!)
                    useCase.activity.toast("이이디나 비밀번호가 일치하지 않습니다.")
                else
                    useCase.activity.toast("서버를 점검중입니다. 잠시후에 시도해주세요")

            }
    }


    fun UserLoginClick(){
        _userClicklogin.call()
    }

    @SuppressLint("CheckResult")
    fun UserLogin(id : String){
                SetIntnet(UserMainActivity::class.java)
                useCase.activity.finishAffinity()
//        loginRepository.UserLogin(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.e("userGson", Gson().toJson(it.location))
//                SetIntnet(UserMainActivity::class.java)
//                useCase.activity.finishAffinity()
//            })
//            {
//                Log.e("userLoginError",it.message)
//                if(it.message?.contains("401")!!)
//                    useCase.activity.toast("존재하지 않는 아이디 입니다.")
//                else
//                    useCase.activity.toast("서버를 점검중입니다. 잠시후에 시도해주세요")
//            }
    }

    fun SetIntnet(activity : Class<*>){
        var intnet = Intent(useCase.activity,activity)
        useCase.activity.startActivity(intnet)
    }
}