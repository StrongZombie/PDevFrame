package hbb.example.test.aop.anno

/**
 * @author HuangJiaHeng
 * @date 2020/3/18.
 */
@Retention(AnnotationRetention.RUNTIME)//存储在编译后的 Class 文件，反射可见。
@Target(AnnotationTarget.FUNCTION)//方法（不包括构造函数）
annotation class CheckLoginK{

}