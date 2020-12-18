package hbb.example.test.coroutines

import android.util.Log
import hbb.example.test.util.ToastUtils
import kotlinx.coroutines.*

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
       GlobalScope.launch(Dispatchers.Main) {
           delay(3000)
           val result= getData("假装有url")//异步协程中获取，并挂起
           ToastUtils.get.showText(result)//主线程中弹窗
       }
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




    fun login(username: String, token: String) {
        // Create a new coroutine to move the execution off the UI thread
      var j =   GlobalScope.launch(Dispatchers.IO) {
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
//            loginRepository.makeLoginRequest(jsonBody) //进行登录请求
        }
//        viewModelScope是封装在viewModel里的CoroutinesScope
//        所有 suspend 函数都必须在协程中执行。
//        由于此协程通过 viewModelScope 启动，因此在 ViewModel 的作用域内执行。如果 ViewModel 因用户离开屏幕而被销毁，则 viewModelScope 会自动取消，且所有运行的协程也会被取消。
    }
}