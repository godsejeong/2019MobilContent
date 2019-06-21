package com.jjmin.mbliecontent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.jjmin.mbliecontent.data.model.ArrangementData

class CustomDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    // 왼쪽,위쪽,위쪽높이,왼쪽너비
    // size 100,200
    // (x,y,x+100,y+200)


//    companion object{
//        var position = 0
//        var paint = Paint()
//    }
//
//    init {
//        paint.isAntiAlias = true
//        paint.isDither = true
//        paint.style = Paint.Style.FILL
//    }
//
//    var x: Int? = null
//    var y: Int? = null
//    var drawtype: Int? = null
//    var drawList : ArrayList<ArrangementData>? = arrayListOf()
//
//    fun setList(x: Int,y : Int,type: Int) {
//        this.x = x
//        this.y = y
//        this.drawtype = type
//
////        drawList?.add(ArrangementData(x!!.toString(),y!!.toString(),position!!.toString(),type!!.toString()))
//////        (0 until drawList?.size!!).forEach {
////            Log.e("position",Gson().toJson(drawList))
////        position++
//        invalidate()
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        var w = width / 2
//        var h = height / 2

//        if (drawtype == 1) {
//            Log.e("draw","Rect")
//            val r = Rect(w-x!!,h-y!!, w + x!!, h + y!!)
//            paint.color = Color.BLACK
//            canvas?.drawRect(r,paint)
//        }else if(drawtype == 2){
//            paint.color = Color.BLACK
//            canvas?.translate((w).toFloat(), (h).toFloat())
//            canvas?.drawCircle(0f,0f,x?.toFloat()!!,paint)
//        }

//        (0 until drawList?.size!!).forEach {
//            var drawX = drawList!![it].x
//            var drawY = drawList!![it].y
//            if(drawList!![it].num == "1"){
//                val r = Rect(w - drawX.toInt(),h - drawY.toInt(), w + drawX.toInt(), h + drawY.toInt())
//                paint.color = Color.BLACK
//                canvas?.drawRect(r,paint)
//            }else if(drawList!![it].num == "2"){
//                paint.color = Color.BLACK
//                canvas?.translate((w).toFloat(),(h).toFloat())
//                canvas?.drawCircle(0f,0f,drawX?.toFloat()!!,paint)
//            }
//        }
    }
//}