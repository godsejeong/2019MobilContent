package com.jjmin.mbliecontent.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.data.model.UserInfo
import com.jjmin.mbliecontent.databinding.ActivityMainBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.sticker.Sticker
import com.jjmin.mbliecontent.util.RealmUtils
import com.jjmin.mbliecontent.util.SharedUtils
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding>() {
    var realm = Realm.getDefaultInstance()
    override val LayoutId = R.layout.activity_main
    var position = 0
    var sendList = ArrayList<SendShapeData>()
    val useCase by lazy { MainUseCase(this) }
    val viewmodel: MainViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel
        viewDataBinding.mainNextBtn.bringToFront()
//        viewDataBinding.mainNextBtn.setOnClickListener {
//            Log.e("list", Gson().toJson(viewDataBinding.slStickerLayout.returnData()))
//        }

        viewmodel.clickNext.observe(this, Observer {
            var list = viewDataBinding.slStickerLayout.returnData()
            sendList.clear()
            (0 until list.size).forEach{
                var id = list[it].id!!
                sendList.add(
                    SendShapeData(
                        list[it].x!!,
                        list[it].y!!,
                        list[it].id!!,
                        list[it].num!!,
                    SharedUtils.getFood(id),
                    SharedUtils.getCountry(id),
                    SharedUtils.getxplan(id),
                    SharedUtils.getAllergy(id),
                    SharedUtils.getMeterial(id)))
            }

            viewmodel.SendServer(sendList,RealmUtils.getCompanyId())
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                var type = data?.getIntExtra("type", 0)
                Log.e("asdfasdfa", "ASDFASdfasdfsd")
                Colordialog(type!!)
            }
        }
    }

    fun setStiker(type: Int, color: Int) {
        var unwrappedDrawable: Drawable? = null
        if (type == 1) {
            unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.shape_rectangle)
        } else if (type == 2) {
            unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.shape_circle)
        }

        var drawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(drawable, color)

        var sticker = Sticker(this, getBitmapFromDrawable(drawable!!, 300, 300),color,position,type)
        viewDataBinding.slStickerLayout.addSticker(sticker)
        position++
    }

    fun Colordialog(type: Int) {
        var color: Int? = null
        ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            .setTitle("ColorPicker Dialog")
            .setPreferenceName("MyColorPickerDialog")
            .setPositiveButton(getString(R.string.confirm),
                ColorEnvelopeListener { envelope, fromUser ->
                    //                    setLayoutColor(envelope)
                    Log.e("color", envelope.color.toString())
                    color = envelope.color
                    setStiker(type, color!!)
                })
            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
                color = null
            }
            .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
            .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
            .show()
    }

    private fun getBitmapFromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy","destory")
        realm.delete(UserInfo::class.java)
        SharedUtils.deletefood(viewDataBinding.slStickerLayout.returnSize()!!)
    }
}
