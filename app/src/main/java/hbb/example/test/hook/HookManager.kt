package hbb.example.test.hook

import android.content.Context
import android.util.Log
import android.view.View
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.math.log


/**
 * @author HuangJiaHeng
 * @date 2020/3/18.
 */
object HookManager {
    fun initHook(c: Context,v:View){

//        //获取listenerInfo
        var getListenerInfo = View::class.java.getDeclaredMethod("getListenerInfo")
        getListenerInfo.isAccessible = true         //由于getListenerInfo()方法并不是public的，所以要加这个代码来保证访问权限
        var listenerInfo = getListenerInfo.invoke(v)
//
//        //通过获取的listenerInfo来获取点击监听
        val listenerInfoClz = Class.forName("android.view.View\$ListenerInfo")// 这是内部类的表示方法
        val field = listenerInfoClz.getDeclaredField("mOnClickListener")
        val onClickListenerInstance =field.get(listenerInfo) as View.OnClickListener

        val proxyListener = Proxy.newProxyInstance(
            c.javaClass.classLoader, arrayOf<Class<*>>(View.OnClickListener::class.java)
        ) { o, method, objects ->
            var now = System.currentTimeMillis()
            Log.e("hook", "hook监听$now")
            method.invoke(onClickListenerInstance, *objects)

        }

        field.set(listenerInfo,proxyListener)

    }

}