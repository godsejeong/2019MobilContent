package com.jjmin.mbliecontent

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.dialog_shape.*


class ShapeDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(PaintDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_shape)

        shapeCircle.setOnClickListener {

            var intent = Intent()
                intent.putExtra("type",2)
                setResult(RESULT_OK, intent)
                finish()
        }

        shapeRectangle.setOnClickListener {

            var intent = Intent()
                intent.putExtra("type",1)
                setResult(RESULT_OK, intent)
                finish()
        }

        shapeStar.setOnClickListener {


            var intent = Intent()
            intent.putExtra("type", 3)
            setResult(RESULT_OK, intent)
            finish()
        }

        shapeTriangle.setOnClickListener {

            var intent = Intent()
            intent.putExtra("type", 4)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
