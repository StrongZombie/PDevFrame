package hbb.example.test.bugly

import android.app.Application
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.PrintWriter
import java.lang.RuntimeException

/**
 * Created by hjh on 2020/8/4
 */
class CrashHandler : Thread.UncaughtExceptionHandler{

    private var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler ?= null
    private lateinit var context:Context


    companion object{

        private val util:CrashHandler by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            CrashHandler()
        }
    }

    fun init(context : Context){
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        defaultUncaughtExceptionHandler?.run {
            Thread.setDefaultUncaughtExceptionHandler(this)
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        val path : String = context.getExternalFilesDir(null)?.path+"/hjh/debug.txt"
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED){
            return
        }
        val f = File(path)
        if (!f.exists()){
            if (!f.createNewFile()){
                throw RuntimeException("日志文件创建失败")
            }
        }
        val pw = PrintWriter(f)
        pw.println("写入时间${System.currentTimeMillis()}"+e.message)
        e.printStackTrace(pw)
        pw.close()
    }

}