package hbb.example.test.viewmodel

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.*
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import hbb.example.test.Image.MyDrawable
import hbb.example.test.Image.PaletteUtil
import hbb.example.test.ImageSwitchView

import hbb.example.test.R
import hbb.example.test.view.OneActivity
import io.reactivex.Single
import kotlin.concurrent.thread

/**
 * @author HuangJiaHeng
 * @date 2020/2/3.
 */
//class MainViewModel (val repo: MainRepository):ViewModel(){
//    var artiId:Int = -1
//    val loading= ObservableBoolean(false)
//    val content = ObservableField<String>()
//    val title = ObservableField<String>()
//
//    fun loadArticle():Single<bean> =
//        repo.(articleId)
//            .async()
//            .doOnSuccess { t: Article? ->
//                t?.let {
//                    title.set(it.title)
//                    content.set(it.content)
//
//                }
//            }
//            .doOnSubscribe { startLoad()}
//            .doAfterTerminate { stopLoad() }
//}
//单绑
@BindingAdapter("url")
fun url (view:View,i:Int){
    var va = view as ImageView
//    val transition= ResourcesCompat.getDrawable(
//        va.context.resources,
//       i,
//        null
//    )
//    if (i == R.drawable.test1){
//        transition as TransitionDrawable
//        transition.startTransition(2000)
//    }
    va.setImageDrawable(MyDrawable())

}
//双绑
@BindingAdapter("app:display")
    fun show(view:View,b:Boolean){
        Log.e("tag",b.toString())
        var vb = View.VISIBLE==view.visibility
        if (vb != b){
            view.visibility = if(b) View.VISIBLE else View.INVISIBLE
        }
    }

@InverseBindingAdapter(attribute = "app:display",event = "app:displayAttrChange")
fun isDispla(v:View):Boolean = v.visibility == View.VISIBLE

@BindingAdapter("app:displayAttrChange",requireAll = false)
fun setVisibleListen(v:View,listener:InverseBindingListener ){
    if (listener !=null){
        var vi = v as ImageSwitchView
        vi.setVisibleListener(object :ImageSwitchView.VisibleListener{
            override fun onChange() {
                Log.e("tag","visibleChange")
                listener.onChange()
            }
        })
    }
}


class MainViewModel{

    var observableInt:ObservableInt = ObservableInt(R.drawable.clip1)
    var b = ObservableBoolean(true)

    fun changeView(v:View){
        observableInt.set(R.drawable.test1)
    }

    fun skipActivity(v:View){
//        v.context.startActivity(Intent(v.context,OneActivity::class.java))
    }
}