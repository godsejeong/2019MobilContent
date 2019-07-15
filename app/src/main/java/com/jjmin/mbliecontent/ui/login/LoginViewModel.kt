package com.jjmin.mbliecontent.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.UserMainActivity
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.data.remote.DeploymentRepository
import com.jjmin.mbliecontent.data.remote.LoginRepository
import com.jjmin.mbliecontent.ui.deployment.ShapeDeploymentActivity
import com.jjmin.mbliecontent.ui.main.MainActivity
import com.jjmin.mbliecontent.ui.register.RegisterActviity
import com.jjmin.mbliecontent.ui.sticker.Sticker
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.jetbrains.anko.toast
import org.json.JSONArray
import java.util.*

class LoginViewModel(val useCase: LoginUseCase,val loginRepository: LoginRepository,val deploymentRepository: DeploymentRepository) : ViewModel(){

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

                user.apply {
                    this.id = it.user?.id!!
                    this.passwd = it.user?.passwd!!
                    this.companyName = it.user?.companyName!!
                    this.phoneNumber = it.user?.phoneNumber!!
                    this.companyEmail = it.user?.companyEmail!!
                    this.token = it.user?.token!!
                }
                mRealm.copyToRealm(user)
                mRealm.commitTransaction()
                useCase.activity.toast("${RealmUtils.getCompanyName()} 로그인 합니다.")
                useCase.activity.finishAffinity()
                SetIntnet(MainActivity::class.java)
            })  {
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
        deploymentRepository.ShapeDeployment("5d2bdc47f92aa01d590b9ecf")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("deploment", Gson().toJson(it))
                try {
                    var jsonArray = JSONArray(it.locates)
                    var list = ArrayList<SendShapeData>()
                    Log.e("jsonArray", jsonArray.toString())

                    var json: ArrayList<SendShapeData> =
                        Gson().fromJson(jsonArray.toString(), object : TypeToken<ArrayList<SendShapeData>>() {}.type)
                    list.addAll(json)

                    var intent = Intent(useCase.activity,ShapeDeploymentActivity::class.java)
                    intent.putExtra("list",list)
                    useCase.activity.startActivity(intent)
                    useCase.activity.finishAffinity()

                } catch (e: NullPointerException) {

                }
            }) {
                useCase.activity.toast("서버가 점검중입니다.")
                Log.e("DeploymentErrorMessage", it.message)
            }
    }

    fun SetIntnet(activity : Class<*>){
        var intnet = Intent(useCase.activity,activity)
        useCase.activity.startActivity(intnet)
    }

    private fun getBitmapFromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }
}