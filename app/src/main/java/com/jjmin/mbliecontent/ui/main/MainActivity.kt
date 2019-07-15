package com.jjmin.mbliecontent.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityMainBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.sticker.Sticker
import com.jjmin.mbliecontent.util.SharedUtils
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import io.realm.Realm
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding>() {
    var realm = Realm.getDefaultInstance()
    override val LayoutId = R.layout.activity_main
    var position = 0
    var sendList = ArrayList<String>()
    val useCase by lazy { MainUseCase(this) }
    val viewmodel: MainViewModel by viewModel { parametersOf(useCase) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel
        viewDataBinding.mainNextBtn.bringToFront()

        viewmodel.clickNext.observe(this, Observer {
            var jsonArray = JsonArray()
            var list = viewDataBinding.slStickerLayout.returnData()
            sendList.clear()

            (0 until list.size).forEach {
                var id = list[it].id!!
                var jsonObj = JsonObject()

                if (SharedUtils.getCountry(id) != "" &&
                    SharedUtils.getxplan(id) != "" &&
                    SharedUtils.getMeterial(id) != "" &&
                    SharedUtils.getAllergy(id) != "" &&
                    SharedUtils.getFood(id) != ""
                ) {
                    jsonObj.addProperty("x", list[it].x!!)
                    jsonObj.addProperty("y", list[it].y!!)
                    jsonObj.addProperty("id", list[it].id)
                    jsonObj.addProperty("num", list[it].num)
                    jsonObj.addProperty("color", list[it].color)
                    jsonObj.addProperty("country", SharedUtils.getCountry(id))
                    jsonObj.addProperty("explain", SharedUtils.getxplan(id))
                    jsonObj.addProperty("material", SharedUtils.getMeterial(id))
                    jsonObj.addProperty("allergy", SharedUtils.getAllergy(id))
                    jsonObj.addProperty("name", SharedUtils.getFood(id))

                    jsonArray.add(jsonObj)

                    Log.e("asdf", Gson().toJson(jsonArray))
                    viewmodel.SendServer(Gson().toJson(jsonArray))
                } else {
                    Toast.makeText(this, "음식정보를 모두 기입해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
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

        var sticker = Sticker(
            this, getBitmapFromDrawable(drawable!!, 300, 300),
            color, position, type
        )
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
}
