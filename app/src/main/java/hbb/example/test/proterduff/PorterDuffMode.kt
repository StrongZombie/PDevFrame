package hbb.example.test.proterduff

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View


/**
 * @author HuangJiaHeng
 * @date 2020/6/10.
 *
 * 切记saveLayer开启透明图层再处理，避免颜色混淆
 * 切记开启硬件加速
 */
class PorterDuffMode(context: Context,attributeSet: AttributeSet?=null) :View(context,attributeSet){


    private var srcPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dstPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * ADD 交集部分颜色相加
     * XOR 交集部分不显示
     * CLEAR 都不显示
     * DARKEN 交集部分昏暗处理
     * LIGHTEN 交集部分明亮处理
     * DST 显示设置mode前的那张 （DST）
     * SRC 显示设置mode后的那张（SRC)
     * XOR 显示不交集部分
     * SRC_ATOP 显示DST部分和SRC被DST覆盖部分  (形状是dst，但是是src覆盖dst)
     * SRC_IN 显示SRC 与DST 交集部分（SRC在上）
     * SRC_OUT 显示SRC 除交集部分以外的东西
     * SRC_OVER 显示SRC在上DST在下的图案
     * DST_ATOP 显示SRC部分和DST被SCR覆盖部分
     * DST_IN
     * DST_OVER
     * DST_OUT
     * MULTIPLY 显示交集部分，并且颜色是混搭在一起的
     * SCREEN 显示所有图案，交集部分留白处理
     * */
    var xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)

    var bitmapSrc:Bitmap
    var bitmapDrc:Bitmap

    init {
//        setLayerType(LAYER_TYPE_HARDWARE,srcPaint)
        bitmapSrc = makeSrc(200,200)
        bitmapDrc = makeDst(200,200)
        srcPaint.setFilterBitmap(false)
        srcPaint.style = Paint.Style.FILL
    }
    companion object {
        private const val W = 200
        private const val H = 200
        private const val ROW_MAX = 4 // number of samples per row
        private val sModes =
            arrayOf<Xfermode>(
                PorterDuffXfermode(PorterDuff.Mode.CLEAR),
                PorterDuffXfermode(PorterDuff.Mode.SRC),
                PorterDuffXfermode(PorterDuff.Mode.DST),
                PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
                PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                PorterDuffXfermode(PorterDuff.Mode.DST_IN),
                PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
                PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
                PorterDuffXfermode(PorterDuff.Mode.XOR),
                PorterDuffXfermode(PorterDuff.Mode.DARKEN),
                PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
                PorterDuffXfermode(PorterDuff.Mode.SCREEN)
            )
        private val sLabels = arrayOf(
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
        )

        // create a bitmap with a circle, used for the "dst" image
        fun makeDst(w: Int, h: Int): Bitmap {
            val bm =
                Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(bm)
            val p =
                Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = -0x33bc
            c.drawOval(RectF(0f, 0f, 100f, 100f), p)
            return bm
        }

        // create a bitmap with a rect, used for the "src" image
        fun makeSrc(w: Int, h: Int): Bitmap {
            val bm =
                Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(bm)
            val p =
                Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = -0x995501
            c.drawRect(RectF(30f, 30f, 100f, 100f), p)
            return bm
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var paint = Paint()
        var saveIndex = canvas?.saveLayer(0f,0f,100f,100f,null)

        canvas?.drawBitmap(bitmapDrc,0f,0f,srcPaint) //drc是圆  src 是方
        srcPaint.xfermode = xfermode
        canvas?.drawBitmap(bitmapSrc,0f,0f,srcPaint)
        srcPaint.xfermode = null
        canvas?.restoreToCount(saveIndex!!)

    }

}