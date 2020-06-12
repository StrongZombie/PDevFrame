package hbb.example.test.customcomponent

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import hbb.example.test.R


/**
 * @author HuangJiaHeng
 * @date 2020/6/9.
 */
class ShaderView(context: Context,attributes: AttributeSet?=null)  : View(context,attributes){

    var TAG ="ShaderView"
    var bigPaint:Paint
    var linePaint :Paint

    private val offset1: Int = ViewConfiguration.get(getContext()).getScaledTouchSlop() /**手指要超过这个距离才进行UI操作，避免频繁*/
    var guaguaPaint :Paint
    var mx = 0f
    var my = 0f
    var guaguaPath:Path


    var offset:Int = 0
    var colors = intArrayOf(Color.parseColor("#4C4D4B"), Color.parseColor("#D9D6DA"), Color.parseColor("#4C4D4B"))
    var positions = floatArrayOf(0.1f,1f,0.1f)


    lateinit var linearGradient:LinearGradient
    lateinit var bitmapShader:BitmapShader
    lateinit var composeShader: ComposeShader
    lateinit var composeComposeShader:ComposeShader
    lateinit var ccPaint:Paint
    lateinit var composePaint :Paint


    lateinit var avoidPaint:Paint
    init {


        /**
         * bitmapShader 刮刮奖
         * */
        guaguaPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        guaguaPaint.style = Paint.Style.STROKE  /**设置划线！！！*/
        guaguaPaint.strokeWidth = 30f
        guaguaPaint.strokeCap = Paint.Cap.ROUND
        var bitmapShader = BitmapShader(BitmapFactory.decodeResource(resources,R.drawable.test),Shader.TileMode.REPEAT,Shader.TileMode.REPEAT)
        guaguaPaint.shader = bitmapShader
        guaguaPath = Path()

        /**
         * lineGradient 线性渲染
         * */
        bigPaint = Paint()
        bigPaint.isAntiAlias=true
        linePaint = Paint()
        linePaint.isAntiAlias = true
        linePaint.textSize = 68f
        var valueAnimator = ValueAnimator();
        valueAnimator.setIntValues(-1000, 1000);
        valueAnimator.duration = 2000;
        valueAnimator.repeatCount = -1;



        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.gbz)
        bitmapShader = BitmapShader(bitmap,Shader.TileMode.REPEAT,Shader.TileMode.REPEAT)
        bigPaint.shader = bitmapShader
        bigPaint.style = Paint.Style.FILL
        bigPaint.strokeWidth = bitmap.height.toFloat()


//        composeShader = ComposeShader(bitmapShader,linearGradient,PorterDuff.Mode.XOR)
//        composePaint = Paint(Paint.ANTI_ALIAS_FLAG)
//        composePaint.style = Paint.Style.FILL
//        composePaint.strokeWidth = 100f
//        composePaint.shader = composeShader


        valueAnimator.addUpdateListener {
            offset = it.animatedValue as Int
            linearGradient = LinearGradient(offset.toFloat(), 300f, offset+1000f, 1000f, colors, null, Shader.TileMode.CLAMP)
            linePaint.shader = linearGradient
            /**
             * composeShader
             * */
            composeShader = ComposeShader(bitmapShader,linearGradient,PorterDuff.Mode.XOR)
            composePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            composePaint.style = Paint.Style.FILL
            composePaint.strokeWidth = 100f
            composePaint.shader = composeShader
            composeComposeShader = ComposeShader(composeShader,bitmapShader,PorterDuff.Mode.ADD)
            ccPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            ccPaint.style = Paint.Style.FILL
            ccPaint.strokeWidth = 100f
            ccPaint.shader = composeComposeShader

            invalidate()
        }
        valueAnimator.start();



    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBitShader(canvas)

        drawLinearShader(canvas)

        drawGuaGua(canvas)

        canvas?.drawLine(10f,500f,500f,500f,composePaint)

        canvas?.drawLine(10f,800f,500f,800f,ccPaint)

    }

    private fun drawGuaGua(canvas: Canvas?){
        canvas?.drawPath(guaguaPath,guaguaPaint)
    }

    private fun drawLinearShader(canvas: Canvas?){
        canvas?.drawText("演示文字",10f,300f,linePaint)
    }

    private fun drawBitShader(canvas: Canvas?){
        canvas?.drawLine(0f,100f,500f,100f,bigPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                guaguaPath.reset()
                val x = event.x
                val y = event.y
                mx = x
                my = y
                guaguaPath.moveTo(x, y)
                Log.e(TAG,"点击的坐标：mx:$mx my:$my")
            }
            MotionEvent.ACTION_MOVE -> {
                val x1 = event.x
                val y1 = event.y
                val preX: Float = mx
                val preY: Float = my
                val dx = Math.abs(x1 - preX)
                val dy = Math.abs(y1 - preY)
                if (dx >= offset1 || dy >= offset1) { // 贝塞尔曲线的控制点为起点和终点的中点
                    val cX = (x1 + preX) / 2
                    val cY = (y1 + preY) / 2
//                    Log.e(TAG,"绘制的坐标：preX：${preX}preY: $preY  cX:$cX cY: $cY")
                    guaguaPath.quadTo(cX,cY,x1, y1)
                    mx = x1
                    my = y1
                }
            }
        }
        Log.e(TAG,"path 的量：${guaguaPath.toString()} ")
        invalidate()
        return true
    }
}