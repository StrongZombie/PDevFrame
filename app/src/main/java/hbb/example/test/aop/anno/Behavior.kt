package hbb.example.test.aop.anno

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

/**
 * @author HuangJiaHeng
 * @date 2020/3/18.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Behavior(val value: String, val type: Int)