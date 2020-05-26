package com.yunkahui.datacubeper.common.utils

import android.content.Context
import android.os.Process
import android.text.TextUtils

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * @author HuangJiaHeng
 * @date 2020/3/31.
 */
object BuglyUtil {

    fun initBugly(context:Context,isDebug:Boolean,AppId:String){
//        CrashReport.initCrashReport(context, AppId, isDebug);
    }

    fun initBuglyWithPackageName(context:Context,isDebug:Boolean,AppId:String){

        // 获取当前包名
        val packageName: String = context.packageName
        // 获取当前进程名
        val processName = getProcessName(Process.myPid())
        // 设置是否为上报进程
//        val strategy = UserStrategy(context)
//        strategy.isUploadProcess = processName == null || processName == packageName
        // 初始化Bugly
//        CrashReport.initCrashReport(context, AppId, isDebug, strategy)
    }

    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}