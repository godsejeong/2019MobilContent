package com.jjmin.mbliecontent.ui.sticker

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

/**
 * 贴纸的动作接口（支持拖动，缩放，旋转，绘制，触摸）
 * Create by: chenWei.li
 * Date: 2019/2/4
 * Time: 12:58 AM
 * Email: lichenwei.me@foxmail.com
 */
interface ISupportOperation {

    /**
     * 平移操作
     *
     * @param dx
     * @param dy
     */
    fun translate(dx: Float, dy: Float)

    /**
     * 缩放操作
     *
     * @param sx
     * @param sy
     */
    fun scale(sx: Float, sy: Float)

    /**
     * 旋转操作
     *
     * @param degrees
     */
    fun rotate(degrees: Float)


    /**
     * 绘制操作
     *
     * @param canvas
     * @param paint
     */
    fun onDraw(canvas: Canvas, paint: Paint)

    /**
     * 触摸操作
     *
     * @param event
     */
    fun onTouch(event: MotionEvent)

}
