package hbb.example.test.transactionAnimation.mainActivity

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hbb.example.test.R
import hbb.example.test.customcomponent.ReboundUtil

import hbb.example.test.transactionAnimation.mainActivity.adapter.MultipleItemAdapter
import hbb.example.test.transactionAnimation.mainActivity.bean.Sample
import kotlinx.android.synthetic.main.transaction_main.*

/**
 * @author HuangJiaHeng
 * @date 2020/2/21.
 */
@BindingAdapter("app:bindAdapter")
fun bindAdapter(v: RecyclerView,data:ObservableArrayList<Sample>){
    v.layoutManager = GridLayoutManager(v.context,3)
    v.adapter = MultipleItemAdapter(data)
}
class TransactionMainVM{
    var data:ObservableArrayList<Sample> = ObservableArrayList()

    fun getData(){
        data.add(Sample(Sample.blue, "蓝色的", R.drawable.blue_circle))
        data.add(Sample(Sample.red, "红色的",R.drawable.red_circle))
        data.add(Sample(Sample.green,"绿色的",R.drawable.green_circle))
        data.add(Sample(Sample.yellow, "黄色的",R.drawable.yellow_circle))

    }

    fun addData(v: View){
        data.add(Sample(Sample.blue, "蓝色的",R.drawable.blue_circle))

        ReboundUtil.getUtil(MainActivity.activity!!).updateRecyclerviewLayoutChange(MainActivity!!.activity!!.rl)
    }
}