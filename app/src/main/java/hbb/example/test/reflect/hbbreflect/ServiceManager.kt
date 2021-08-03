package hbb.example.test.reflect.hbbreflect

import android.os.IBinder
import android.os.IInterface

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/7/21
 *   desc  :
 * </pre>
 *
 */
object ServiceManager {

    var TYPE: Class<*> =
        RefClass.load(ServiceManager::class.java, "android.os.ServiceManager")

    @MethodParams([String::class, IBinder::class])
    var addService: RefStaticMethod<Void>? = null


}