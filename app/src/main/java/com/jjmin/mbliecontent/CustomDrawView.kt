package com.jjmin.mbliecontent

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.google.gson.Gson
import android.graphics.Matrix
import com.jjmin.mbliecontent.data.model.ArrangementData
import com.jjmin.mbliecontent.ui.sticker.ISupportOperation

class CustomDrawView(context: Context, attrs: AttributeSet,bitmap: Bitmap) : View(context, attrs) {
    private val mDelBitmap: Bitmap//스티커 이미지
    var isFocus: Boolean = false//드로어 여부
    protected var mMode: Int = 0//当前模式
//  val matrix : Matrix
    private val mSrcPoints: FloatArray//矩阵变换前的点坐标
    private val mDstPoints: FloatArray//矩阵变换后的点坐标
    val stickerBitmapBound: RectF//스티커 범위
    val delBitmapBound: RectF//삭제 범위
    private val mMidPointF: PointF//스티거 중심 좌표


    init {
        mMidPointF = PointF()

        mSrcPoints = floatArrayOf(
            0f, 0f, //左上
            bitmap.width.toFloat(), 0f, //右上
            bitmap.width.toFloat(), bitmap.height.toFloat(), //右下
            0f, bitmap.height.toFloat(), //左下
            (bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat()//中间点
        )
        mDstPoints = mSrcPoints.clone()
        stickerBitmapBound = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())

        mDelBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.icon_delete)
        delBitmapBound = RectF(
            (0 - mDelBitmap.width / 2 - PADDING).toFloat(),
            (0 - mDelBitmap.height / 2 - PADDING).toFloat(),
            (mDelBitmap.width / 2 + PADDING).toFloat(),
            (mDelBitmap.height / 2 + PADDING).toFloat()
        )

        //스티커 메모를 화면 가운데로 이동
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        matrix.postTranslate(100f,200f)
//        updatePoints()
        //将贴纸默认缩小1/2
//        scale(0.5f, 0.5f)
    }
    companion object {

        val MODE_NONE = 0//初始状态
        val MODE_SINGLE = 1//标志是否可移动
        val MODE_MULTIPLE = 2//标志是否可缩放，旋转

        private val PADDING = 30//避免图像与边框太近，这里设置一个边距
    }
}