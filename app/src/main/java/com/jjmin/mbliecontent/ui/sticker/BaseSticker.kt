package com.jjmin.mbliecontent.ui.sticker

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics
import android.view.WindowManager
import com.jjmin.mbliecontent.R

abstract class BaseSticker(
    context: Context,
    bitmap: Bitmap//스티커 이미지
) : ISupportOperation {
    private val mDelBitmap: Bitmap//스티커 이미지
    val matrix: Matrix//이미지 변환 감시
    var isFocus: Boolean = false//드로어 여부
    protected var mMode: Int = 0//当前模式
    private val mSrcPoints: FloatArray//矩阵变换前的点坐标
    private val mDstPoints: FloatArray//矩阵变换后的点坐标
    val stickerBitmapBound: RectF//스티커 범위
    val delBitmapBound: RectF//삭제 범위
    private val mMidPointF: PointF//스티거 중심 좌표
    val bitmap = bitmap

    init {
        matrix = Matrix()
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
        val dx = (displayMetrics.widthPixels / 2 - this.bitmap.width / 2).toFloat()
        val dy = (displayMetrics.heightPixels / 2 - this.bitmap.height / 2).toFloat()
        translate(dx, dy)
        //将贴纸默认缩小1/2
        scale(0.5f, 0.5f)
    }

    /**
     * 平移操作
     *
     * @param dx
     * @param dy
     */
    override fun translate(dx: Float, dy: Float) {
        matrix.postTranslate(dx, dy)
        updatePoints()
    }

    /**
     * 缩放操作
     *
     * @param sx
     * @param sy
     */
    override fun scale(sx: Float, sy: Float) {
        matrix.postScale(sx, sy, mMidPointF.x, mMidPointF.y)
        updatePoints()
    }

    /**
     * 旋转操作
     *
     * @param degrees
     */
    override fun rotate(degrees: Float) {
        matrix.postRotate(degrees, mMidPointF.x, mMidPointF.y)
        updatePoints()
    }

    /**
     * 当矩阵发生变化的时候，更新坐标点（src坐标点经过matrix映射变成了dst坐标点）
     */
    private fun updatePoints() {
        //更新贴纸点坐标
        matrix.mapPoints(mDstPoints, mSrcPoints)
        //更新贴纸中心点坐标
        mMidPointF.set(mDstPoints[8], mDstPoints[9])
    }

    /**
     * 绘制贴纸自身
     *
     * @param canvas
     * @param paint
     */
    override fun onDraw(canvas: Canvas, paint: Paint) {
        //绘制贴纸
        canvas.drawBitmap(bitmap, matrix, paint)
        if (isFocus) {
            //스티커 테두리 그리기
            canvas.drawLine(
                mDstPoints[0] - PADDING,
                mDstPoints[1] - PADDING,
                mDstPoints[2] + PADDING,
                mDstPoints[3] - PADDING,
                paint
            )
            canvas.drawLine(
                mDstPoints[2] + PADDING,
                mDstPoints[3] - PADDING,
                mDstPoints[4] + PADDING,
                mDstPoints[5] + PADDING,
                paint
            )
            canvas.drawLine(
                mDstPoints[4] + PADDING,
                mDstPoints[5] + PADDING,
                mDstPoints[6] - PADDING,
                mDstPoints[7] + PADDING,
                paint
            )
            canvas.drawLine(
                mDstPoints[6] - PADDING,
                mDstPoints[7] + PADDING,
                mDstPoints[0] - PADDING,
                mDstPoints[1] - PADDING,
                paint
            )

            //지우기 버튼 그리기
            canvas.drawBitmap(
                mDelBitmap,
                mDstPoints[0] - (mDelBitmap.width / 2).toFloat() - PADDING.toFloat(),
                mDstPoints[1] - (mDelBitmap.height / 2).toFloat() - PADDING.toFloat(),
                paint
            )
        }
    }

    companion object {

        val MODE_NONE = 0//初始状态
        val MODE_SINGLE = 1//标志是否可移动
        val MODE_MULTIPLE = 2//标志是否可缩放，旋转

        private val PADDING = 30//避免图像与边框太近，这里设置一个边距
    }
}