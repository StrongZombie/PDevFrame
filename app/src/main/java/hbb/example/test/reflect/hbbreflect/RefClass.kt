package hbb.example.test.reflect.hbbreflect

import android.util.Log
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.util.*

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/7/21
 *   desc  :
 * </pre>
 *
 */
object RefClass {

    private val REF_TYPES = HashMap<Class<*>, Constructor<*>>()

    init {
        try {
            REF_TYPES[RefObject::class.java] =
                RefObject::class.java.getConstructor(Class::class.java, Field::class.java)
//            REF_TYPES.put(
//                RefMethod::class.java,
//                RefMethod::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefInt::class.java,
//                RefInt::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefLong::class.java,
//                RefLong::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefFloat::class.java,
//                RefFloat::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefDouble::class.java,
//                RefDouble::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefBoolean::class.java,
//                RefBoolean::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefStaticObject::class.java,
//                RefStaticObject::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefStaticInt::class.java,
//                RefStaticInt::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefStaticMethod::class.java,
//                RefStaticMethod::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
//            REF_TYPES.put(
//                RefConstructor::class.java,
//                RefConstructor::class.java.getConstructor(Class::class.java, Field::class.java)
//            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    /**
     * 加载类型
     *
     * @param clazz 本地.class文件
     * @param targetClass 反射的目标
     * */
    fun load(clazz: Class<*>, targetClass: String):Class<*>{
        val fields = clazz.declaredFields
        val target = Class.forName(targetClass)
        for (field in fields){

        }
        return clazz
    }
}