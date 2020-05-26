package hbb.example.test.transactionAnimation.mainActivity.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.databinding.ObservableArrayList
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import hbb.example.test.R
import hbb.example.test.aop.anno.SingleClick
import hbb.example.test.databinding.TwoActivityBinding
import hbb.example.test.transactionAnimation.mainActivity.MainActivity
import hbb.example.test.transactionAnimation.mainActivity.MainActivity.Companion.activity
import hbb.example.test.transactionAnimation.oneActivity.OneActivity
import hbb.example.test.transactionAnimation.mainActivity.bean.Sample
import hbb.example.test.transactionAnimation.twoactivity.TwoActivity

/**
 * @author HuangJiaHeng
 * @date 2020/2/22.
 */
class MultipleItemAdapter : BaseMultiItemQuickAdapter<Sample, BaseViewHolder> {
    companion object{
        var adapter:MultipleItemAdapter?=null
    }

    constructor(data:ObservableArrayList<Sample>):super(data){
        addItemType(Sample.blue, R.layout.transaction_red)
        addItemType(Sample.green,R.layout.transaction_red)
        addItemType(Sample.red,R.layout.transaction_red)
        addItemType(Sample.yellow,R.layout.transaction_red)
        adapter = this

    }

    override fun convert(helper: BaseViewHolder?, item: Sample?) {
        helper?.getView<View>(R.id.v_bg)?.background = ContextCompat.getDrawable(mContext,item!!.color)
        helper?.setText(R.id.tv_name,item?.name)


        helper?.itemView?.setOnClickListener    @SingleClick(value = 1000L){
//            Log.e()
            when(item.type){
                Sample.blue -> transactionAnimation(item,helper.layoutPosition,helper)
                Sample.red -> tranSactionAnimationRed(item,helper)
            }
        }
    }


    fun tranSactionAnimationRed(item:Sample?,helper: BaseViewHolder?){
        val i = Intent(mContext, TwoActivity::class.java)
        i.putExtra("sample", item)
        mContext.startActivity(i)
    }

    fun transactionAnimation(item:Sample?,index:Int,helper: BaseViewHolder?){
        var p = Pair<View,String>(helper?.getView(R.id.v_bg),"color")
        var n =Pair<View,String>(helper?.getView(R.id.tv_name),"name")

       var array =  arrayOf<Pair<View,String>>(p,n)
        val i = Intent(mContext, OneActivity::class.java)
        i.putExtra("sample", item)
        val transitionActivityOptions =
          ActivityOptionsCompat.makeSceneTransitionAnimation(
              MainActivity.activity!!,
              *array
          )
        mContext.startActivity(i,transitionActivityOptions.toBundle())
    }

}