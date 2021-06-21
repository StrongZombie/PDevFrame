package hbb.example.test.eventmanager


/**
 * <pre>
 *   author: hjh
 *   time  : 2021/1/14
 *   desc  :
 * </pre>
 *
 */
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subscribe (

        val ThreadMode : Int
){
        companion object{
                const val NEW_THREAD:Int = 1002
                const val MAIN_THREAD:Int  = 1001
        }
}