package com.jjmin.mbliecontent.ui.shape

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.jjmin.mbliecontent.ui.food.FoodInfoActivity
import com.jjmin.mbliecontent.ui.sticker.BaseSticker

class Shape(context: Context, bitmap: Bitmap, color: Int, id: Int, num: Int, x: Float, y: Float) :
    BaseShape(context, bitmap, x, y) {

    private val mLastSinglePoint = PointF()//스크린의 커튼을 가리키는 점
    private val mLastDistanceVector = PointF()//백테계산
    private val mDistanceVector = PointF()//두손가락 사이의 백터
    private var mLastDistance: Float = 0.toFloat()//손가락 사이의 길이
    var x : Float? = 0f
    var y : Float? = 0f
    var num : Int = num
    var color = color
    var id = id

    private val mFirstPoint = PointF()
    private val mSecondPoint = PointF()

    /**
     * 초기화
     */
    fun reset() {
        mLastSinglePoint.set(0f, 0f)
        mLastDistanceVector.set(0f, 0f)
        mDistanceVector.set(0f, 0f)
        mLastDistance = 0f
        mMode = MODE_NONE
    }

    /**
     */
    fun calculateDistance(firstPointF: PointF, secondPointF: PointF): Float {
        val x = firstPointF.x - secondPointF.x
        val y = firstPointF.y - secondPointF.y
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }


    /**
     *
     * @param lastVector
     * @param currentVector
     * @return
     */

    fun calculateDegrees(lastVector: PointF, currentVector: PointF): Float {
        val lastDegrees = Math.atan2(lastVector.y.toDouble(), lastVector.x.toDouble()).toFloat()
        val currentDegrees = Math.atan2(currentVector.y.toDouble(), currentVector.x.toDouble()).toFloat()
        return Math.toDegrees((currentDegrees - lastDegrees).toDouble()).toFloat()
    }

    var gestureDetector = GestureDetector(object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            var intent =  Intent(context, FoodInfoActivity::class.java)
            intent.putExtra("id",id)
            context?.startActivity(intent)
            return true
        }
    })

    /**
     *
     * @param event
     */
    override fun onTouch(event: MotionEvent) {
        gestureDetector.onTouchEvent(event)
    }
}
