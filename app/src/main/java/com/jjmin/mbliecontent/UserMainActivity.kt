package com.jjmin.mbliecontent

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.jjmin.mbliecontent.ui.sticker.Sticker
import kotlinx.android.synthetic.main.activity_main.*

class UserMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        var unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.shape_rectangle)
        var drawable = DrawableCompat.wrap(unwrappedDrawable!!)
        var sticker = Sticker(this,getBitmapFromDrawable(drawable!!, 300, 300),-65525,10,2)
        sl_sticker_layout.addSticker(sticker)

        var sticker1 = Sticker(this,getBitmapFromDrawable(drawable!!, 500, 500),-65525,10,2)
        sl_sticker_layout.addSticker(sticker1)
    }

    private fun getBitmapFromDrawable(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }
}
