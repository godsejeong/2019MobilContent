package com.jjmin.mbliecontent.ui.deployment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.data.remote.DeploymentRepository
import com.jjmin.mbliecontent.ui.main.MainActivity
import com.jjmin.mbliecontent.ui.shape.Shape
import com.jjmin.mbliecontent.util.RealmUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import org.json.JSONArray

class DeploymentViewmodel(val useCase: DeploymentUseCase,val repository: DeploymentRepository) : ViewModel(){

    var statusText: ObservableField<String> = ObservableField()
    var statussubText : ObservableField<String> = ObservableField()
    var isvisible : ObservableField<Boolean> = ObservableField()

    val shapesettingBtn = View.OnClickListener {
        var intent = Intent(useCase.activity, MainActivity::class.java)
        useCase.activity.startActivity(intent)
    }

    init {
        useCase.activity.viewDataBinding.ShapeLayout.removeAllShape()
        statussubText.set("배치한 도형이 없습니다.")
        ShapeDeployment()
        isvisible.set(true)

    }

    @SuppressLint("CheckResult")
    fun ShapeDeployment(){
        repository.ShapeDeployment(RealmUtils.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                Log.e("deploment", Gson().toJson(it))
                try {
                    var jsonArray = JSONArray(it.locates)
                    var list = ArrayList<SendShapeData>()
                    Log.e("jsonArray", jsonArray.toString())

                    var json: ArrayList<SendShapeData> =
                        Gson().fromJson(jsonArray.toString(), object : TypeToken<ArrayList<SendShapeData>>() {}.type)
                    list.addAll(json)

                    (0 until list.size).forEach { it ->
                       var array =list[it]
                        var unwrappedDrawable: Drawable? = null
                        if (array.num == 1) {
                            unwrappedDrawable = AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_rectangle)
                        } else if (array.num == 2) {
                            unwrappedDrawable = AppCompatResources.getDrawable(useCase.activity, R.drawable.shape_circle)
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

                        var shape = Shape(useCase.activity,getBitmapFromDrawable(drawable!!, 300, 300),
                            array.color,array.id,array.num,array.x,array.y)
                            useCase.activity.viewDataBinding.ShapeLayout.addShare(shape)
                    }
                }catch (e : NullPointerException){

                }
                when {
                    it.message == "SUCCESS" -> {
                        statusText.set("수정하기")
                        isvisible.set(false)
                    }
                    it.message == "no data" -> statusText.set("배치하기")
                    it.message == "no id" -> statusText.set("배치하기")
                }

            }){
                useCase.activity.toast("서버가 점검중입니다.")
                Log.e("DeploymentErrorMessage",it.message)
            }
    }

    private fun getBitmapFromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }


}