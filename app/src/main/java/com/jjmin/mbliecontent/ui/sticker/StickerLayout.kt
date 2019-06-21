package com.jjmin.mbliecontent.ui.sticker

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

class StickerLayout : View, View.OnTouchListener {

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
    private var mStick: Sticker? = null

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
        return StickerManager.instance?.stickerList?.size
    }

    /**
     * 添加贴纸
     *
     * @param sticker
     */
    fun addSticker(sticker: Sticker) {
        this.id = sticker.id
        Log.e("idasfgasdfkjhasgdf", id.toString())
        StickerManager.instance?.addSticker(sticker)
        StickerManager.instance?.setFocusSticker(sticker)
        invalidate()
    }

    /**
     * 移除贴纸（只有在贴纸聚焦的时候才可以删除，避免误触碰）
     *
     * @param sticker
     */
    fun removeSticker(sticker: Sticker) {
        if (sticker.isFocus) {
            StickerManager.instance?.removeSticker(sticker)
            invalidate()
        }
    }

    fun returnData() : List<Sticker>{
        var list = StickerManager.instance?.stickerList
        (0 until list?.size!!).forEach{
            Log.e("asdf","x : ${list[it].x}  y : ${list[it].y} color : ${list[it].color}")
        }
        return list
    }

    /**
     * 清空贴纸
     */
    fun removeAllSticker() {
        StickerManager.instance?.removeAllSticker()
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val stickerList = StickerManager.instance?.stickerList
        var focusSticker: Sticker? = null
        for (i in stickerList?.indices!!) {
            val sticker = stickerList[i]
            if (sticker.isFocus) {
                focusSticker = sticker
            } else {
                sticker.onDraw(canvas, paint)
            }
        }
        focusSticker?.onDraw(canvas, paint)
    }



    override fun onTouch(v: View, event: MotionEvent): Boolean {

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    //判断是否按到删除按钮 - 삭제 버튼 눌렀늕 ㅣ체크
                    mStick = StickerManager.instance?.getDelButton(event.x, event.y)
                    if (mStick != null) {
                        removeSticker(mStick!!)
                        mStick = null
                    }
                    //单指是否触摸到贴纸
                    mStick = StickerManager.instance?.getSticker(event.x, event.y)
                    if (mStick == null) {
                        if (event.pointerCount == 2) {
                            Log.e("Log", "안녕")
                            //处理双指触摸屏幕，第一指没有触摸到贴纸，第二指触摸到贴纸情况
                            mStick = StickerManager.instance?.getSticker(event.getX(1), event.getY(1))
                        }
                    }
                    if (mStick != null) {
                        StickerManager.instance?.setFocusSticker(mStick!!)
                    }
                }
                else -> {
                }
            }
            if (mStick != null) {
                mStick!!.onTouch(event)
            } else {
                StickerManager.instance?.clearAllFocus()
            }
            invalidate()
        return true
    }
}