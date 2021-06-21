package hbb.example.test.eventmanager.activity

import android.os.Bundle
import android.util.Log
import hbb.example.test.coroutines.CoroutinesGuide
import hbb.example.test.databinding.ActEventbusBinding
import hbb.example.test.eventmanager.EventManager
import hbb.example.test.eventmanager.Subscribe

/**
 * <pre>
 *   author: hjh
 *   time  : 2020/12/11
 *   desc  :
 * </pre>
 *
 */

class MainActivity : A() {

    private val actEventbusBinding:ActEventbusBinding by lazy {
        ActEventbusBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(actEventbusBinding.root)
        super.onCreate(savedInstanceState)
        EventManager.INSTANCE.register(this)
        actEventbusBinding.btnPose.isEnabled = false
        actEventbusBinding.btnPose.setOnTouchListener { v, event ->
            Log.e("TAG","EEEE")
            return@setOnTouchListener false
        }
        actEventbusBinding.btnPose.setOnClickListener {
            Log.e("TAG","DDD")
            val i:Int = 1
            EventManager.INSTANCE.post(i)
        }
        actEventbusBinding.clRoot.setOnClickListener {
            Log.e("TAG","CCC")
        }
        actEventbusBinding.clRoot.setOnTouchListener { v, event ->
            Log.e("TAG","ddd")
            return@setOnTouchListener false
        }
        CoroutinesGuide.show1()
    }

    private fun convertType(eventType: Class<*>): Class<*>? {
        var returnClass = eventType
        when (eventType) {
            Boolean::class.javaPrimitiveType -> {
                returnClass = Boolean::class.java
            }
            Int::class.javaPrimitiveType -> {
                returnClass = Int::class.java
            }
            Float::class.javaPrimitiveType -> {
                returnClass = Float::class.java
            }
            Double::class.javaPrimitiveType -> {
                returnClass = Double::class.java
            }
        }
        return returnClass
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.INSTANCE.unregister(this)
    }

    @Subscribe(ThreadMode = Subscribe.MAIN_THREAD)
    fun doinMainThread(s: String){
        actEventbusBinding.btnPose.text = s
    }

    @Subscribe(ThreadMode = Subscribe.NEW_THREAD)
    fun doinNewThread(int: Int){
        actEventbusBinding.btnPose.text = "s:$int"
    }

    inner class sa{
        fun sa(){

        }
    }
}
