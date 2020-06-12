package hbb.example.test.http

import android.content.Context
import retrofit2.Retrofit

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import hbb.example.test.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient

/**
 * @author HuangJiaHeng
 * @date 2020/2/5.
 * */
class RetrofitUtil {
    companion object{
        /**
         * 超时时间
         * */
        const val DEFAULT_TIMEOUT = 10L

        lateinit var retrofit:Retrofit
        lateinit var appContext: Context

        val util:RetrofitUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitUtil()
        }

        fun init(context: Context){
            appContext=context
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(UrlManager.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }


    /**
     * 获取OkHttpClient对象
     * @return okHttpClient
     * */
    private fun getOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)//错误重联
            .addInterceptor(getLogInterceptor()) //日志输出
            .cookieJar(getCookieJar()) //Cookie管理
            .build()
    }

    /**
     * 自动Cookie管理
     * @return PersistentCookieJar
     * */
    private fun getCookieJar():PersistentCookieJar{
        return  PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }

    /**
     * 打印拦截器 release下不打印
     * @return HttpLoggingInterceptor
     * */
    private fun getLogInterceptor():HttpLoggingInterceptor{
        val logInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return logInterceptor
    }

}