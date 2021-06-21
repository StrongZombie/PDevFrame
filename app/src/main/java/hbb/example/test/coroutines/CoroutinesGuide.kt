package hbb.example.test.coroutines

import android.util.Log
import hbb.example.test.util.ToastUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.Exception
import kotlin.system.measureTimeMillis

/**
 *
 * Created by hjh on 2020/8/5
 * 协程
 * */
object  CoroutinesGuide {
    const val TAG = "CoroutinesGuide"
    val job = Job()
    val scope = CoroutineScope(job + Dispatchers.Main)
    fun show1(){
//       GlobalScope.launch(Dispatchers.Main) {
//           delay(3000)
//           val result= getData("假装有url")//异步协程中获取，并挂起
//           ToastUtils.get.showText(result)//主线程中弹窗
//       }
//        scope.launch {
//            try {
//                ToastUtils.get.showText( concurrentSum().toString())
//
//            }catch (e: Exception){
//                ToastUtils.get.showText( "error")
//
//            }
//
//        }
//        startPandC()

//            lock()
//        scope.launch {
//            exception()
//        }
//        catchException()
//        cancel()
//        supervisorJob()
//        yield()
//        scopeGuide()
        ActorsGuilde.main()
    }

    private suspend fun getData(url:String) = withContext(Dispatchers.IO){
        for(i in 0..10){
            delay(1000)
            Log.e(TAG,"在IO协程里获取数据，获取到数据：123456")
            return@withContext "123456"
    }
        return@withContext "123"
    }

    /**
     * async  await
     * */
    fun show2(){
        scope.launch (Dispatchers.Main) {
            val listData = get2DataAll("加载有url")
            ToastUtils.get.showText("listData"+listData.size)
        }
        job.cancel()
    }

    /**
     * 同时访问两个接口，要等一起返回
     * */
    private suspend fun get2Data(url:String) = coroutineScope {
        val getOne = async {
            delay(1000)
            return@async "123456"
        }
        val getTwo = async {
            delay(2000)
            return@async "78910"
        }
        val result1 = getOne.await()
        val result2 = getTwo.await()
        return@coroutineScope arrayListOf(result1,result2)
    }


    /**
     * 同时访问两个接口，要等一起返回
     * */
    private suspend fun get2DataAll(url:String) = coroutineScope {
        val aList = arrayListOf(
            async {
                delay(1000)
                return@async "123456"
            },
            async {
                delay(2000)
                return@async "78910"
            }
        )
        return@coroutineScope aList.awaitAll()
    }

    private suspend fun doSomethingUsefulOne(): Int{
        try{
            delay(100)
            return 12
        }finally {
            Log.e(TAG, "CANCEL")
//            ToastUtils.get.showText("one_cancel")
        }
    }


    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1500)
        return 13
    }

    private suspend fun doSomethingUsefulTwoException(): Int{
        throw ArithmeticException()
    }


    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwoException() }
        one.await() + two.await()
    }

    suspend fun simple(): List<Int> {
        delay(1000) // 假装我们在这里做了一些异步的事情
        return listOf(1, 2, 3)
    }

    fun main() = runBlocking<Unit> {
        simple().forEach { value -> Log.e(TAG,value.toString()) }

    }

//    //flow 异步流
//    fun simple(): Flow<Int> = flow { // 流构建器
//        for (i in 1..3) {
//            delay(100) // 假装我们在这里做了一些有用的事情
//            emit(i) // 发送下一个值
//        }
//    }
//     收集这个流
//    simple().collect { value -> println(value) }
//    (1..3).asFlow().collect { value -> println(value) }
    fun login(username: String, token: String) {
    // Create a new coroutine to move the execution off the UI thread
    var j = GlobalScope.launch(Dispatchers.IO) {
        val jsonBody = "{ username: \"$username\", token: \"$token\"}"
//            loginRepository.makeLoginRequest(jsonBody) //进行登录请求
    }
    }
//        viewModelScope是封装在viewModel里的CoroutinesScope
//        所有 suspend 函数都必须在协程中执行。
//        由于此协程通过 viewModelScope 启动，因此在 ViewModel 的作用域内执行。如果 ViewModel 因用户离开屏幕而被销毁，则 viewModelScope 会自动取消，且所有运行的协程也会被取消。
    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) {
            Log.e(TAG, "生产")
            send(x++) // 在流中开始从 1 生产无穷多个整数
        }
    }
    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (x in numbers) {
            Log.e(TAG, "加工")
            send(x * x)
        }
    }

    fun startPandC() {
        scope.launch {

            val numbers = produceNumbers() // 从 1 开始生成整数
            val squares = square(numbers) // 整数求平方
            repeat(5) {
                delay(2000)
                Log.e(TAG, "消费："+squares.receive()) // 输出前五个
            }
            Log.e(TAG, "Done!") // 输出前五个
            coroutineContext.cancelChildren() // 取消子协程
        }
    }

    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
//        Log.e(TAG,)
    }

    fun lock(){
        val mutex = Mutex()
        runBlocking {
            var counter = 0
            withContext(Dispatchers.Default) {
                massiveRun {
                    mutex.withLock {
                        counter++
                    }
                }
            }
            Log.e(TAG,"Counter = $counter")
        }
    }

    suspend fun mute(action: suspend () -> Unit){
        val mutex = Mutex()
        mutex.withLock {
            action()
        }
    }


    suspend fun exception(){

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG,"CoroutineExceptionHandler got $exception")
        }

        try {
//            val job = GlobalScope.launch { // launch 根协程
//
//                println("Throwing exception from launch")
//                throw IndexOutOfBoundsException() // 我们将在控制台打印 Thread.defaultUncaughtExceptionHandler  //执行时直接自动捕获（自动传播异常）由默认的CoroutineExceptionHandler捕获除了
//            }
//            job.join()
//            println("Joined failed job")
            val deferred = GlobalScope.async { // async 根协程
                println("Throwing exception from async")
                throw ArithmeticException() // 没有打印任何东西，依赖用户去调用等待
            }
            try {
                deferred.await()   //用户暴露异常
                println("Unreached")
            } catch (e: ArithmeticException) {
                println("Caught ArithmeticException")
            }
        }catch (e:Exception){

        }

    }

    fun catchException(){
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG,"CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // 根协程，运行在 GlobalScope 中
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) { // 同样是根协程，但使用 async 代替了 launch
            throw ArithmeticException() // 没有打印任何东西，依赖用户去调用 deferred.await()
        }
        scope.launch {
            joinAll(job, deferred)
        }
    }

    fun cancel(){
        scope.launch {
            val job = scope.launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        println("Child is cancelled")
                    }
                }
                yield() //让步，运行子协程
                println("Cancelling child")
                child.cancel()
                child.join()
                yield()
                println("Parent is not cancelled")
            }
            job.join()
        }

    }

    fun supervisorJob(){
        scope.launch {
            val supervisor = SupervisorJob()
//            supervisorScope =======  withContext(coroutineContext + supervisor)
//            withContext(coroutineContext + supervisor)
            supervisorScope {
                // 启动第一个子作业——这个示例将会忽略它的异常（不要在实践中这么做！）
                val firstChild = launch(CoroutineExceptionHandler { _, _ ->  }) {
                    println("The first child is failing")
                    throw AssertionError("The first child is cancelled")
                }
                // 启动第二个子作业
                val secondChild = launch {
                    firstChild.join()
                    // 取消了第一个子作业且没有传播给第二个子作业
                    println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        // 但是取消了监督的传播
                        println("The second child is cancelled because the supervisor was cancelled")
                    }
                }
                // 等待直到第一个子作业失败且执行完成
                firstChild.join()
                println("Cancelling the supervisor")
                supervisor.cancel()
                secondChild.join()
            }
        }
    }


    fun yieldGuide(){

        val singleDispatcher = newSingleThreadContext("Single")
        runBlocking {
            val job = GlobalScope.launch {
                launch {
                    withContext(singleDispatcher) {
                        repeat(3) {
                            printSomeThingBlock("Task1")
//                            yield()
                        }
                    }
                }

                launch {
                    withContext(singleDispatcher) {
                        repeat(3) {
                            printSomeThingBlock("Task2")
//                            yield()
                        }
                    }
                }
            }

            job.join()
        }
    }

    suspend fun printSomeThingBlock(text: String) {
        println(text)
        Thread.sleep(1000)
    }

    fun scopeGuide(){
        scope.launch {
            try {
                supervisorScope {
                    val child = launch {
                        try {
                            println("The child is sleeping")
                            delay(Long.MAX_VALUE)
                        } finally {
                            println("The child is cancelled")
                        }
                    }
                    // 使用 yield 来给我们的子作业一个机会来执行打印
                    yield()
                    println("Throwing an exception from the scope")
                    throw AssertionError()
                }
            } catch(e: AssertionError) {
                println("Caught an assertion error")
            }

        }
    }

}