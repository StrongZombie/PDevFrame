package hbb.example.test.download.base

import android.util.Log
import hbb.example.test.download.http.DownloadHttp
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException

/**
 * Created by hjh on 2020/8/5
 */
 abstract class BaseDownCoroutines :IDownInterface{

    private val job = Job()
    private val mainScope = CoroutineScope(job + Dispatchers.IO)
    private var fileSize:Long = 0

    @Throws(IOException::class)
    fun startRequest() {
        mainScope.launch(Dispatchers.IO){
            val response = DownloadHttp.instance.request(getUrl(),getFileName())
            if (response == null){
                onDownFail("下载地址错误")
                return@launch
            }
            if (!response.isSuccessful){
                onDownFail("下载地址错误")
                return@launch
            }
            if (response.body == null){
                onDownFail("下载地址错误")
                return@launch
            }

            fileSize = response.body!!.contentLength()
            val f = File(DownloadHttp.instance.getDownoFileSavePath()+getFileName())
            Log.e("DownCoroutines","下载的文件存放目录：${DownloadHttp.instance.getDownoFileSavePath()+getFileName()}\t 大小：${fileSize/1024f/1024f} MB")
            if (f.exists()) {
                //断点续传
                f.delete()
            }
//            }else{
                try {
                    if (f.createNewFile()){
                        val inputStream = response.body!!.byteStream()
                        val buffer = ByteArray(1024)
                        var downSize :Long =0
                        var speed:Int
                        while (inputStream.read(buffer)
                                .also { speed = it }!=-1){
                            f.outputStream().write(buffer,0,speed)
                            downSize += speed
                            onProgress(fileSize,downSize,speed)
                        }
                        onProgress(fileSize,fileSize,0)
                        onDownFinish()
                    }else{
                        onDownFail("创建文件夹失败")
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    onDownFail("下载失败")
                }
//            }
        }
    }


    private fun mainCoroutinesListener(){

    }

    abstract fun getUrl():String

    abstract fun getFileName():String

}