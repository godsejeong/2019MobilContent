package com.jjmin.mbliecontent.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.jjmin.mbliecontent.R

class ShapeDraw(context :Context) : View(context) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var paint = Paint()
        paint.color = context.getColor(R.color.colorPrimary)
        canvas?.drawRect(500f,600f,500f,600f, paint)
    }
}