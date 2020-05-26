package hbb.example.test.Image

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.View
import hbb.example.test.R
import java.util.jar.Attributes

/**
 * @author HuangJiaHeng
 * @date 2020/2/8.
 */
class CustomDrawableView : View {
    @JvmOverloads
    constructor(context: Context,attributes: AttributeSet?=null):super(context,attributes)
    private val drawable: ShapeDrawable = run {
        val x = 10
        val y = 10
        val width = 300
        val height = 50
        contentDescription = "这个是描述"

        ShapeDrawable(OvalShape()).apply {
            // If the color isn't set, the shape uses black as the default.
            paint.color = 0xff74AC23.toInt()
            // If the bounds aren't set, the shape can't be drawn.
            setBounds(x, y, x + width, y + height)
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawable.draw(canvas)
    }
}