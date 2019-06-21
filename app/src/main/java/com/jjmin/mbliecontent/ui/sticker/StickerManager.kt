package com.jjmin.mbliecontent.ui.sticker

import android.graphics.Matrix

import java.util.ArrayList

/**
 * 贴纸素材管理器
 * Create by: chenWei.li
 * Date: 2019/2/3
 * Time: 8:44 PM
 * Email: lichenwei.me@foxmail.com
 */
class StickerManager {

    private val mStickerList = ArrayList<Sticker>()

    val stickerList: List<Sticker>
        get() = mStickerList

    fun addSticker(sticker: Sticker) {
        mStickerList.add(sticker)
    }

    /**
     * 移除指定贴纸
     *
     * @param sticker
     */
    fun removeSticker(sticker: Sticker) {
        val bitmap = sticker.bitmap
        if (bitmap != null && bitmap.isRecycled) {
            bitmap.recycle()
        }
        mStickerList.remove(sticker)

    }

    /**
     * 移除所有贴纸
     */
    fun removeAllSticker() {
        for (i in mStickerList.indices) {
            val bitmap = mStickerList[i].bitmap
            if (bitmap != null && bitmap.isRecycled) {
                bitmap.recycle()
            }
        }
        mStickerList.clear()
    }

    /**
     * 设置当前贴纸为焦点贴纸
     *
     * @param focusSticker
     */
    fun setFocusSticker(focusSticker: Sticker) {
        for (i in mStickerList.indices) {
            val sticker = mStickerList[i]
            if (sticker == focusSticker) {
                sticker.isFocus = true
            } else {
                sticker.isFocus = false
            }
        }
    }

    /**
     * 清除所有焦点
     */
    fun clearAllFocus() {
        for (i in mStickerList.indices) {
            val sticker = mStickerList[i]
            sticker.isFocus = false
        }
    }

    /**
     * 根据触摸坐标返回当前触摸的贴纸
     *
     * @param x
     * @param y
     * @return
     */
    fun getSticker(x: Float, y: Float): Sticker? {

        val dstPoints = FloatArray(2)
        val srcPoints = floatArrayOf(x, y)

        for (i in mStickerList.indices.reversed()) {
            val sticker = mStickerList[i]
            val matrix = Matrix()
            sticker.matrix.invert(matrix)
            matrix.mapPoints(dstPoints, srcPoints)
            if (sticker.stickerBitmapBound.contains(dstPoints[0], dstPoints[1])) {
                return sticker
            }
        }
        return null
    }

    /**
     * 根据触摸是否触摸到删除按钮，返回对应删除按钮的贴纸
     *
     * @param x
     * @param y
     * @return
     */
    fun getDelButton(x: Float, y: Float): Sticker? {

        val dstPoints = FloatArray(2)
        val srcPoints = floatArrayOf(x, y)

        for (i in mStickerList.indices.reversed()) {
            val sticker = mStickerList[i]
            val matrix = Matrix()
            sticker.matrix.invert(matrix)
            matrix.mapPoints(dstPoints, srcPoints)
            if (sticker.delBitmapBound.contains(dstPoints[0], dstPoints[1])) {
                return sticker
            }
        }
        return null

    }

    companion object {

        @Volatile
        private var mInstance: StickerManager? = null

        val instance: StickerManager?
            get() {
                if (mInstance == null) {
                    synchronized(StickerManager::class.java) {
                        if (mInstance == null) {
                            mInstance =
                                StickerManager()
                        }
                    }
                }
                return mInstance
            }
    }


}
