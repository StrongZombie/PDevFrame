package hbb.example.test.transactionAnimation.mainActivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


import hbb.example.test.R

import hbb.example.test.aop.anno.CheckLoginK
import hbb.example.test.aop.anno.SingleClick


import hbb.example.test.customcomponent.ReboundUtil

import hbb.example.test.databinding.TransactionMainBinding
import hbb.example.test.hook.HookManager
import hbb.example.test.proxy.Deveolp
import hbb.example.test.proxy.JavaDevep
import hbb.example.test.structure.binarysearchTree.BinarySearchTree

import kotlinx.android.synthetic.main.one_activity.tool_bar
import kotlinx.android.synthetic.main.transaction_main.*
import java.lang.reflect.Proxy

/**
 * @author HuangJiaHeng
 * @date 2020/2/21.
 */
class MainActivity :AppCompatActivity(){
    companion object{
        var activity: Activity?=null
    }
    private lateinit var  dataBinding:TransactionMainBinding
    private lateinit var vm:TransactionMainVM


    override fun onCreate(savedInstanceState: Bundle?) {

        dataBinding = DataBindingUtil.setContentView(this,R.layout.transaction_main)
        super.onCreate(savedInstanceState)
        vm = TransactionMainVM()
        dataBinding.vm = vm

        vm.getData()


        activity = this

        initToolbar()
        initTranstion()
        initScrollListener()


        var j = JavaDevep()
        var pj=  Proxy.newProxyInstance(j.javaClass.classLoader,j.javaClass.interfaces
        ) {
              proxy, method, args ->
          //proxy，代理后的实例对象。
          //method，对象被调用方法。
          //args，调用时的参数。
            if (method.name == "code"){
                Log.e("deve","resetCode")
                method.invoke(j)
            }else{
                null
            }
        } as Deveolp
        pj.code()
        pj.debug()
//        var test = Algorithm()
//        var q =test.quicekSort(test.data,0,test.data.size-1)
//        for (i in q){
//            Log.e("tag",i.toString())
//        }


        test()
        dataBinding.toolBar.setOnClickListener {
            Log.e("hook","点击")
        }
        HookManager.initHook(this, dataBinding.toolBar)


    }
    @CheckLoginK
    fun test(){
        Log.i("CheckLogin", "qqq ")
    }

    fun initScrollListener(){
        var tv = TextView(this)
        var lp = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,500)
        tv.background = ColorDrawable(Color.BLUE)
        tv.layoutParams = lp
        var btv = TextView(this)
        var blp = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,500)
        btv.background = ColorDrawable(Color.GREEN)
        btv.layoutParams = lp

        ReboundUtil.getUtil(this)
            .addRebound(this,ReboundUtil.SLIDING,rl)
            .setHeadBoundView(tv)
            .setBottomBoundView(btv)
            .setBoundType(ReboundUtil.HEADER_REBOUND_LIMIT,ReboundUtil.BOTTOM_REBOUND_LIMIT)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initTranstion(){
        val slideTransition = android.transition.Slide()
        slideTransition.slideEdge = Gravity.LEFT
        slideTransition.duration = 500
        window.reenterTransition = slideTransition
        window.exitTransition = slideTransition
    }

    fun initToolbar(){
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

}