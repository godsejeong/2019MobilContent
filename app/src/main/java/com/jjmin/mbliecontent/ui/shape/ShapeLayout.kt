package com.jjmin.mbliecontent.ui.shape

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.jjmin.mbliecontent.ui.food.FoodInfoActivity

class ShapeLayout : View, View.OnTouchListener {

    private var mContext: Context? = null
    var id  : Int? = 0
    var paint: Paint? = null
        get() {
            if (field == null) {
                paint = Paint(Paint.ANTI_ALIAS_FLAG)
                field!!.color = Color.BLACK
                field!!.strokeWidth = 2f
            }
            return field
        }

    //记录当前操作的贴纸对象
    private var mStick: Shape? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    /**
     * 初始化操作
     */
    private fun init(context: Context) {
        this.mContext = context
        //设置触摸监听
        setOnTouchListener(this)
    }

    fun returnSize() : Int? {
        return ShapeManager.instance?.stickerList?.size
    }

    /**
     * 添加贴纸
     *
     * @param sticker
     */
    fun addShare(shape : Shape) {
        this.id = shape.id
        Log.e("idasfgasdfkjhasgdf", id.toString())
        ShapeManager.instance?.addSticker(shape)
        ShapeManager.instance?.setFocusSticker(shape)
        invalidate()
    }

    /**
     * 移除贴纸（只有在贴纸聚焦的时候才可以删除，避免误触碰）
     *
     * @param sticker
     */
    fun removeSticker(shape: Shape) {
        if (shape.isFocus) {
            ShapeManager.instance?.removeSticker(shape)
            invalidate()
        }
    }

    fun returnData() : List<Shape>{
        var list = ShapeManager.instance?.stickerList
        (0 until list?.size!!).forEach{
            Log.e("asdf","x : ${list[it].x}  y : ${list[it].y} color : ${list[it].color}")
        }
        return list
    }

    /**
     * 清空贴纸
     */
    fun removeAllShape() {
        ShapeManager.instance?.removeAllSticker()
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val stickerList = ShapeManager.instance?.stickerList
        var focusSticker: Shape? = null
        for (i in stickerList?.indices!!) {
            val sticker = stickerList[i]
            if (sticker.isFocus) {
                focusSticker = sticker
            } else {
                sticker.onDraw(canvas, paint!!)
            }
        }
        focusSticker?.onDraw(canvas, paint!!)
    }



    override fun onTouch(v: View, event: MotionEvent): Boolean {

        return true
    }
}