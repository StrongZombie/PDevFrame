package hbb.example.test.aop.anno

/**
 * @author HuangJiaHeng
 * @date 2020/4/26.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SingleClick (val value:Long)