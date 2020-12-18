package hbb.example.test.download.http

import okhttp3.Response

/**
 * Created by hjh on 2020/8/5
 */
interface IDownload {

    fun getDownoFileSavePath():String

    fun init()

    suspend fun request(url: String,fileName:String): Response?
}