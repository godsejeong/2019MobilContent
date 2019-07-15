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
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.ui.main.MainActivity
import com.jjmin.mbliecontent.ui.shape.Shape
import com.jjmin.mbliecontent.util.TTSUtils

class DeploymentViewmodel(val useCase: DeploymentUseCase) : ViewModel() {
    var tts = TTSUtils
    var isvisible: ObservableField<Boolean> = ObservableField()
    var statussubText : ObservableField<String> = ObservableField()

    val shapesettingBtn = View.OnClickListener {
        var intent = Intent(useCase.activity, MainActivity::class.java)
        useCase.activity.startActivity(intent)
    }

    init {
        tts.speak("환영합니다. " +
                "찾고 싶으신 음식을 찾아드립니다. " +
                "도형을 더블클릭하여 음식의 정보를 보고 사방향 클릭을 이용해 음식의 세부정보를 볼 수 있습니다.")
        isvisible.set(true)
        statussubText.set("배치한 도형이 없습니다.")
        useCase.activity.viewDataBinding.ShapeLayout.removeAllShape()
        ShapeDeployment()
    }

    @SuppressLint("CheckResult")
    fun ShapeDeployment() {
        var list = useCase.list
        (0 until list.size).forEach { it ->
            var array = list[it]
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

            var shape = Shape(
                useCase.activity, getBitmapFromDrawable(drawable!!, 300, 300),
                array.color, array.id, array.num, array.x, array.y,array.name,array.allergy,array.material,array.explain,array.country)
            useCase.activity.viewDataBinding.ShapeLayout.addShare(shape)
            isvisible.set(false)

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
