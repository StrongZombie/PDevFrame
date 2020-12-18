package hbb.example.test.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *   author: hjh
 *   time  : 2020/9/27
 *   desc  :
 * </pre>
 *
 */
class StickItemDecoration(val context: Context)  : RecyclerView.ItemDecoration(){

    val paint = Paint()
    val overPaint = Paint()
    var width =0
    var currentV :View ?= null
    var nextV:View ?=null

    init {
        width = context.resources.displayMetrics.widthPixels
        paint.color = Color.argb(.8f,0f,0f,0f)
        overPaint.color = Color.argb(.8f,1f,0f,0f)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
//        for ( i in 0 until parent.childCount){
//            val view = parent.getChildAt(i)
//            c.drawRect(0f,view.top.toFloat()+view.height,width.toFloat(),view.top.toFloat()+view.height+100f,paint)
//        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val lm =  parent.layoutManager
        for (i  in 0 until parent.childCount){
            val view = parent.getChildAt(i)
                if (view.tag == "header"){
                    if (currentV!=null){
                        Log.e("TAG","VIEW"+view.top+"top"+"currentV!!.height\t"+currentV!!.height)
                    }

                    if (view.top <= 0){
                        currentV = view
                        break
                    }
                    if (currentV!=null){
                        if (view.top >0){
                            nextV = view
                            break
                        }
                    }
                }
        }
        if (nextV!=null && currentV !=null){
            if (nextV!!.top >0f && nextV!!.top <= currentV!!.height){
                c.translate(0f,nextV!!.top-currentV!!.height.toFloat())
            }else if (nextV!!.top<0f){
                c.translate(0f,0f)
            }
        }
        currentV?.draw(c)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
//        outRect.bottom = 100
    }

}