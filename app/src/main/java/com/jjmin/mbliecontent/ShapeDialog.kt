package com.jjmin.mbliecontent

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.dialog_shape.*
import org.jetbrains.anko.toast

class ShapeDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_shape)

        shapeCircle.setOnClickListener {

//            Colordialog()

                var intent = Intent()
                intent.putExtra("type",2)
                setResult(RESULT_OK, intent)
                finish()
        }

        shapeRectangle.setOnClickListener {

//            Colordialog()

                var intent = Intent()
                intent.putExtra("type",1)
                setResult(RESULT_OK, intent)
                finish()
        }
    }
}
