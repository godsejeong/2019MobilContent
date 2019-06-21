package com.jjmin.mbliecontent.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
import com.jjmin.mbliecontent.databinding.ActivityMainBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.sticker.Sticker
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val LayoutId = R.layout.activity_main

    val useCase by lazy { MainUseCase(this) }
    val viewmodel: MainViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel
        viewDataBinding.mainNextBtn.bringToFront()
        viewDataBinding.mainNextBtn.setOnClickListener {
            Log.e("list", Gson().toJson(viewDataBinding.slStickerLayout.returnData()))
        }

        viewmodel.clickNext.observe(this, Observer {

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

        var sticker = Sticker(this, getBitmapFromDrawable(drawable!!, 300, 300),color)
        viewDataBinding.slStickerLayout.addSticker(sticker)
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
