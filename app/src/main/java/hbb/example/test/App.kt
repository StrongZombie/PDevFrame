package hbb.example.test

import android.content.Context
import android.os.Environment
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import hbb.example.test.download.http.DownloadHttp
import hbb.example.test.util.ToastUtils
import java.io.File

/**
 * @author HuangJiaHeng
 * @date 2020/3/17.
 */
class App : MultiDexApplication(){

    companion object{
        lateinit var app:Context
    }

    override fun onCreate() {
        super.onCreate()
        app = this.applicationContext
        ToastUtils.get.init(this)
//        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
        ARouter.init(this);

        DownloadHttp.Companion.Builder()
            .timeOut(10000)
            .showLogInterceptor()
            .setDownloadPath(getExternalFilesDir(null)?.absolutePath+ File.separator+"Download")
            .build()
            .init()

    }
}