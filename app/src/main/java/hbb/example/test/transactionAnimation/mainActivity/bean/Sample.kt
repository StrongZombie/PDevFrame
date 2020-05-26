package hbb.example.test.transactionAnimation.mainActivity.bean

import androidx.databinding.ObservableField
import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * @author HuangJiaHeng
 * @date 2020/2/22.
 */
 class Sample(var type:Int,var name:String,var color:Int):MultiItemEntity,Serializable{
    companion object{
        val red:Int = 1
        val blue:Int = 2
        val green:Int = 3
        val yellow:Int = 4

    }
    override fun getItemType(): Int {
       return type
    }

}