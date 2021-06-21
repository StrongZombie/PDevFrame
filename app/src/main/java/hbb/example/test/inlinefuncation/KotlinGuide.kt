package hbb.example.test.inlinefuncation

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import java.io.File

object KotlinGuide{
    private const val TAG = "KotlinGuide"
    /**
     * 将对象作为函数的参数，在函数内可以通过 this指代该对象。返回值为函数的最后一行或return表达式。
     * */
    fun withFun(){
        var p = Paint();
        with(p){
            color = Color.BLACK
            isAntiAlias = true
        }
        //c也是paint
        var list = with(mutableListOf<String>()){
            add("1")
            this
        }
    }

    /**
     * 传递一个函数参数，如果函数返回true就返回对象  不然为null
     * 函数必须返回boolean
     * takeUnless 想反  true返回null false 返回对象
     * takeIf
     * */
    fun takeIfFun(){

        // foo?:bar  foo ！= null 为foo ==null 为bar
        var file = File("").takeIf { it.exists() }?:return

        var filer = File("").takeUnless { false }?:return
    }

    /**
     * repeat
     * 重复调用次数
     * 可以和协程一起用
     * */
    fun repeatFun(){
        repeat(5){
            Log.e(TAG,it.toString())
        }
    }


    /**
     * 调用run 函数返回的是组最是最后一行或者return
     * 返回值参数之没做要求
     * 对象是this
     * 返回值为 随便
     * */
    fun runFun(){
        var file = File("").run { Log.e(TAG,absoluteFile.path)
            Log.e(TAG,"ddd")
            return@run "123"
        }
    }

    /**
     * let
     * 调用对象（T）的let函数，则该对象为函数的参数。在函数内可以通过 it 指代该对象。返回值为函数的最后一行或指定return表达式
     * 对象作为it
     * 有返回值 随便
     * */
    fun letFun(){

        var f = File("")
        f.let {

            print(f.absolutePath)
            return@let "1"
        }
    }

    /**
     * apply
     * 对象的apply函数，可以调用对象的任意方法
     * 对象是this
     * 无返回值
     * */
    fun applyFun(){
        var f =File("")
        f.apply {

            return@apply
        }
    }

    /**
     * also
     * it为对象 无返回值
     * */
    fun alsoFun(){
        var f = File("")
        f.also {
            it.absoluteFile
            return@also
        }
    }

    /**
     * 协程
     * 类型线程那样，但是更加轻量级
     * 协程就是这个原理，
     * 当某个任务遇到阻塞时，它会自动切换到其他任务，
     * 当其他任务遇到阻塞时再切换到另一个任务，相当于是异步的。
     * */
    fun coroutines(){
//        //第一种  runBlocking 直接阻塞当前线程开启一个协程
//        runBlocking {
//            //如果使用launch 则变成异步
//            launch {
//
//            }
//            repeat(8){
//                Log.e(TAG,".")
//            }
//        }
//
//        //第二种，launch一个异步协程
//        //上下文  Dispatchers.Default（默认）  Dispatchers.IO（优化处理IO）  Dispatchers.MAIN（主线程）  设置环境
//        var job = GlobalScope.launch(Dispatchers.Default){
//            //不阻塞开启协程
//        }
//        job.cancel()
//        //随时可以取消
//
//
//        //第三种 ，async await  需要在launch 或者runblocking中使用
//        GlobalScope.launch {
//            repeat(1000){
//                delay(1000)
//                var job1 = GlobalScope.async {
//                    getResult()
//                }
//                //获取一个协程的返回值
////                Log.e(TAG,"job1.await():${job1.await()}")
//
//            }
//
//            }

        //附带：suspend关键字
        runBlocking {
            var job2 = GlobalScope.async {
                var a = getResult()
                Log.e(TAG,"A:$a")
            }

            job2.await()
        }

        }
    private suspend  fun getResult():String{
        //附带：suspend关键字
        delay(1000)
        return "await"
    }

    fun getNumResult(result:(Int,Int)->Int):Int{
        return result(1,2)
    }

    fun log(){
        Log.e("TAG", getNumResult { i, i2 -> i+i2 }.toString())
    }

}