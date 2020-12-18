package hbb.example.test.download.base

import android.util.Log

/**
 * Created by hjh on 2020/8/10
 */
class DownCoroutines : BaseDownCoroutines(){

    companion object{
        private const val TAG = "DownCoroutines"
    }

    fun startDownLoad(){
        Log.e(TAG,"onDownStart:下载开始\t")
        startRequest()
    }

    override fun getUrl(): String {
        return "https://sitdown.1yuan.cn/down/org/5"
    }

    override fun getFileName(): String {
        return "SDK.apk"
    }

    override fun onProgress(total: Long, progress: Long, speed: Int) {
        Log.e(TAG,"总大小:${total}字节\t已下载:${progress}\t速度:${speed}")
    }

    override fun onDownFinish() {
        Log.e(TAG,"onDownFinish")
    }

    override fun onDownFail(message: String) {
        Log.e(TAG,"onDownFail:${message}\t")
    }

    override fun onDownStop() {
        Log.e(TAG,"onDownStop")
    }

    override fun onDownPause() {
        Log.e(TAG,"onDownPause")
    }

}