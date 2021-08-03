package hbb.example.test.objload

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import hbb.example.test.databinding.ActGiftStklBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/7/15
 *   desc  :
 * </pre>
 *
 */
class MainActivity: AppCompatActivity() {

    val binding: ActGiftStklBinding by lazy {
        ActGiftStklBinding.inflate(layoutInflater)
    }
    companion object{
        init {
            //需要加载这个，才能使用里面的函数
            System.loadLibrary("test01")
        }
    }


    //这个和c++里面的函数名字，有对应关系。到时候调用安卓里面的函数hello，就能调用到C++里面的函数
    external fun hello(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tv.text = hello()

        val h = Handler()
        for (z in 0..1000){
            h.post {
                for (j in 1..100){
                    Log.e("TAG",j.toString()+"\taaaa\t:${z}\t${Thread.currentThread().name}")
                }
            }
        }

        val timeCost = measureTimeMillis {
            for (i in 0.. 1000){
                GlobalScope.launch(Dispatchers.Main) {
                    for (j in 1..100){
                        Log.e("TAG",j.toString()+"\tddd\t:${i}\t${Thread.currentThread().name}")
                    }
                }
            }
        }


    }

}
