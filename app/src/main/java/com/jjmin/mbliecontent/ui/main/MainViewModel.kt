package com.jjmin.mbliecontent.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.ShapeDialog
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.data.remote.DeploymentRepository
import com.jjmin.mbliecontent.data.remote.MainRepository
import com.jjmin.mbliecontent.ui.sticker.Sticker
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SingleLiveEvent
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import org.json.JSONArray

class MainViewModel(
    val useCase: MainUseCase,
    val mainRepository: MainRepository,
    val deploymentRepository: DeploymentRepository
) : ViewModel() {
    var position = 0
    val _clickNext = SingleLiveEvent<Any>()
    val clickNext : LiveData<Any> get() = _clickNext

    val FabClick = View.OnClickListener {
        SetIntnet(ShapeDialog::class.java,100)
    }

    val logout = View.OnLongClickListener {
        userDelete()
        useCase.activity.toast("로그아웃이 완료되었습니다.")
        useCase.activity.finish()
        return@OnLongClickListener true
    }

    init {
        useCase.activity.viewDataBinding.slStickerLayout.removeAllSticker()
        initShape()
    }

    fun userDelete() {
        var userinfo = RealmUtils.realm.where(UserInfo::class.java).findAll()
        RealmUtils.realm.executeTransaction {
            userinfo.deleteAllFromRealm()
        }
    }

    fun NextClick(){
        _clickNext.call()
    }

    fun SendServer(array : String){
        Log.e("token",RealmUtils.getToken())
        Log.e("jsonArray",Gson().toJson(array))
        mainRepository.SendShape(array,RealmUtils.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                useCase.activity.toast("정상적으로 서버에 반영되었습니다.")
                Log.e("success", it.success.toString())
            }){
                useCase.activity.toast("서버가 점검중입니다.")
                Log.e("MainErrorMessage",it.message)
            }
    }

    fun initShape() {
        deploymentRepository.ShapeDeployment(RealmUtils.getToken())
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
                    position = list.size
                    (0 until list.size).forEach { it ->
                        var array = list[it]
                        var unwrappedDrawable: Drawable? = null
                        if (array.num == 1) {
                            unwrappedDrawable =
                                AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_rectangle)
                        } else if (array.num == 2) {
                            unwrappedDrawable =
                                AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_circle)
                        } else if (array.num == 3) {
                            unwrappedDrawable =
                                AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_star)
                        } else if (array.num == 4) {
                            unwrappedDrawable =
                                AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_triangle)
                        }

                        var drawable = DrawableCompat.wrap(unwrappedDrawable!!)
                        DrawableCompat.setTint(drawable, array.color)
                        Log.e("color", array.color.toString())

                        var sticker = Sticker(
                            useCase.activity, getBitmapFromDrawable(drawable!!, 300, 300),
                            array.color, array.id, array.num, array.x, array.y
                        )
                        useCase.activity.viewDataBinding.slStickerLayout.addSticker(sticker)
                    }
                } catch (e: NullPointerException) {
                }
            }) {
                useCase.activity.toast("서버가 점검중입니다.")
                Log.e("DeploymentErrorMessage", it.message)
            }
    }

    fun setStiker(type: Int, color: Int) {
        var unwrappedDrawable: Drawable? = null
        if (type == 1) {
            unwrappedDrawable = AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_rectangle)
        } else if (type == 2) {
            unwrappedDrawable = AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_circle)
        } else if (type == 3) {
            unwrappedDrawable =
                AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_star)
        } else if (type == 4) {
            unwrappedDrawable =
                AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_triangle)
        }

        var drawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(drawable, color)

        var sticker = Sticker(
            useCase.activity, getBitmapFromDrawable(drawable!!, 300, 300),
            color, position, type, null, null
        )
        useCase.activity.viewDataBinding.slStickerLayout.addSticker(sticker)
        position++
    }

    fun Colordialog(type: Int) {
        var color: Int? = null
        ColorPickerDialog.Builder(useCase.activity, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            .setTitle("ColorPicker Dialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton(useCase.activity.getString(R.string.confirm),
                ColorEnvelopeListener { envelope, fromUser ->
                    //                    setLayoutColor(envelope)
                    Log.e("color", envelope.color.toString())
                    color = envelope.color
                    setStiker(type, color!!)
                })
            .setNegativeButton(
                useCase.activity.getString(R.string.cancel)
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
                color = null
            }
            .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
            .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
            .show()
    }

    fun SetIntnet(activity : Class<*>,requestCode : Int){
        var intnet = Intent(useCase.activity,activity)
        useCase.activity.startActivityForResult(intnet,requestCode)
    }

    private fun getBitmapFromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }
}