package hbb.example.test.customcomponent

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import hbb.example.test.R

/**
 * @author HuangJiaHeng
 * @date 2020/6/9.
 */
class ShaderViewJ(
    context: Context?,
    attrs: AttributeSet?=null
) : View(context, attrs) {
    private val paint: Paint
    private val path: Path
    private var mX = 0f
    private var mY = 0f
    private val offset =
        ViewConfiguration.get(getContext()).scaledTouchSlop.toFloat()
    var bitmap: Bitmap
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                val x = event.x
                val y = event.y
                mX = x
                mY = y
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                val x1 = event.x
                val y1 = event.y
                val preX = mX
                val preY = mY
                val dx = Math.abs(x1 - preX)
                val dy = Math.abs(y1 - preY)
                if (dx >= offset || dy >= offset) { // 贝塞尔曲线的控制点为起点和终点的中点
                    val cX = (x1 + preX) / 2
                    val cY = (y1 + preY) / 2
                    path.quadTo(preX, preY, cX, cY)
                    mX = x1
                    mY = y1
                }
            }
        }
        invalidate()
        return true
    }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        paint.strokeCap = Paint.Cap.ROUND
        bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.test
        )
        val shader = BitmapShader(
            bitmap,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
        paint.shader = shader
        path = Path()
    }
}