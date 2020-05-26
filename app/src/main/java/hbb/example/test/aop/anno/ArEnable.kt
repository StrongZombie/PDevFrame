package hbb.example.test.aop.anno

import android.app.Activity

/**
 * @author HuangJiaHeng
 * @date 2020/4/29.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ArEnable()
annotation class RequirePermission(val permission:Array<String>)
annotation class CheckArCodeInstall()