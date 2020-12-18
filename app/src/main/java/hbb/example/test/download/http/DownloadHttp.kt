package hbb.example.test.download.http

import hbb.example.test.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by hjh on 2020/8/5
 *
 * desc: 封装下载所需的请求类，及下载文件存放地址
 */
@Suppress("unused")
class DownloadHttp : IDownload{
    private var path:String ?= ""
    private var okHttpClient:OkHttpClient ?= null
    private var okHttpClientBuilder  = OkHttpClient.Builder()

    companion object{

        class Builder{

            fun timeOut(time:Long):Builder{
                instance.okHttpClientBuilder.readTimeout(time,TimeUnit.MILLISECONDS)
                instance.okHttpClientBuilder.writeTimeout(time,TimeUnit.MILLISECONDS)
                instance.okHttpClientBuilder.callTimeout(time,TimeUnit.MILLISECONDS)
                return this
            }


            fun addInterceptor(interceptor:Interceptor):Builder{
                instance.okHttpClientBuilder.addInterceptor(interceptor)
                return this
            }

            fun setDownloadPath(path:String?):Builder{
                instance.path = path
                return this
            }

            fun showLogInterceptor():Builder{
                val logInterceptor = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) {
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
                }
                instance.okHttpClientBuilder.addInterceptor(logInterceptor)
                return this
            }

            fun build():DownloadHttp{
                return instance
            }
        }

         val instance:DownloadHttp by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DownloadHttp()
        }
    }



    override fun getDownoFileSavePath():String {
        requireNotNull(path)
        return path!!
    }


    override suspend fun request(url: String,fileName:String) = withContext(Dispatchers.IO){
        val request = Request.Builder().url(url).build()
        val response = okHttpClient?.newCall(request)?.execute()

        if (response!!.isSuccessful && response.body!=null){
            return@withContext response
        }
            return@withContext null
    }


    override fun init() {
       requireNotNull(path)
        if (!path!!.endsWith("/")){
            path = "$path/"
        }
        val f = File(path!!)
        if (!f.exists()){
            f.mkdirs()
        }
       okHttpClient = okHttpClientBuilder.build()
    }




}