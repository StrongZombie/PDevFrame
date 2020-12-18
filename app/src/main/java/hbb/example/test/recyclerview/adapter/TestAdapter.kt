package hbb.example.test.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hbb.example.test.databinding.ItemContentBinding
import hbb.example.test.databinding.ItemHeaderBinding
import hbb.example.test.recyclerview.bean.MultiBean

/**
 * <pre>
 *   author: hjh
 *   time  : 2020/9/27
 *   desc  :
 * </pre>
 *
 */
class TestAdapter(var dataArray:ArrayList<MultiBean>,var context: Context)  : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return Vm(ItemHeaderBinding.inflate(LayoutInflater.from(context),parent,false))
        }
            return CVm(ItemContentBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    override fun getItemCount(): Int {
        return  dataArray .size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("TAG",dataArray[position].header)
        if (holder is Vm){
            holder.itemBinding.vm = dataArray[position].header
        }else if (holder is CVm){
            holder.itemBinding.vm = dataArray[position].header
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (dataArray[position].isHeader){
            return 1
        }
        return 0
    }

}

class Vm(val itemBinding: ItemHeaderBinding) : RecyclerView.ViewHolder(itemBinding.root){
    init {
        itemBinding.root.tag = "header"
    }
}
class CVm(val itemBinding: ItemContentBinding) : RecyclerView.ViewHolder(itemBinding.root)