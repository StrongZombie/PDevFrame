package hbb.example.test.eventmanager

import android.os.Looper
import android.util.Log
import hbb.example.test.BuildConfig
import hbb.example.test.eventmanager.bean.SubscribeBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Method

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/1/14
 *   desc  :
 * </pre>
 *
 */
class EventManager {

    private val subscribeMap: HashMap<Any, Map<Method, Subscribe>> by lazy {
        HashMap<Any, Map<Method, Subscribe>>()
    }

    companion object {
        private const val TAG = "EventManager"
        val INSTANCE: EventManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            EventManager()
        }
    }


    /**
     * 注册
     * */
    fun register(key: Any) {
        subscribeMap[key] = getKeyMethod(key.javaClass)

    }

    fun unregister(key: Any) {
        subscribeMap.remove(key)
    }

    fun post(any: Any) {
        for (i in subscribeMap.keys) {
            //clazz是方法对应维护的 map<Method, annotation>
            val clazz = subscribeMap[i]
            clazz?.let {
                val subscribeInfo = checkIsPostMethod(clazz, any)
                subscribeInfo?.let {
                    callMethod(subscribeInfo, i)
                }
            }
        }
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

    private fun callMethod(subscribeBean: SubscribeBean, i: Any){
        val mode = subscribeBean.annotation.ThreadMode
        if (mode == Subscribe.MAIN_THREAD){
            if (Looper.myLooper() == Looper.getMainLooper()){
                subscribeBean.method.invoke(i, subscribeBean.callInfo)
            }else{
                GlobalScope.launch(Dispatchers.Main) {
                    subscribeBean.method.invoke(i, subscribeBean.callInfo)
                }
            }
        }else if (mode == Subscribe.NEW_THREAD){
            GlobalScope.launch(Dispatchers.IO) {
                subscribeBean.method.invoke(i, subscribeBean.callInfo)
            }
        }
    }

    private fun checkIsPostMethod(methodInfo: Map<Method, Subscribe>, targetObject: Any):SubscribeBean ?{
        for (i in methodInfo.keys){
            //i是方法信息  methodInfo[i] 是annotation
            val parameters = i.parameterTypes
            if (parameters.size > 1 || parameters.isEmpty()){
                continue
            }else{
                if (targetObject.javaClass == parameters[0]){
                    val anna = methodInfo[i]
                    anna?.let {
                        return SubscribeBean(i, anna, targetObject)
                    }
                }
            }
        }
        return null
    }

    fun post(any: Any, tag: String) {

    }

    private fun getKeyMethod(clazz: Class<*>): HashMap<Method, Subscribe> {
        val map = HashMap<Method, Subscribe>()
        for (i in clazz.declaredMethods) {
            val sub = i.getAnnotation(Subscribe::class.java)
            if (sub != null) {
                map[i] = sub
            }
        }
        return map
    }

    private fun log(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }
}