package hbb.example.test

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import kotlin.concurrent.thread

/**
 * @author HuangJiaHeng
 * @date 2020/1/18.
 */
class ImageSwitchView :ImageView{
    var animationSet:AnimationSet?=null
    var duration = 200
    var listener:VisibleListener ? = null

    @JvmOverloads constructor(context: Context,attributeSet: AttributeSet?=null,default:Int=0 ):super(context,attributeSet,default)
    {
        animationSet = AnimationSet(false)
    }

    fun setVisibleListener(listener:VisibleListener){
        this.listener = listener
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        var clip = this.drawable as ClipDrawable
        thread {
            for (i in 0..10000 step 50){
                Thread.sleep(10)
                this.post {
                    clip.level = i
                }
            }
        }
    }

    @Override
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Log.e("tag","viewVisibleChange ${listener==null}")
        listener?.onChange()
    }



    interface VisibleListener{
        fun onChange()
    }
}