package com.yunkahui.datacubeper.common.utils


import android.app.Activity
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * @author HuangJiaHeng
 * @date 2020/1/11.
 */
class CaheUtil {
    companion object{
        val util by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            CaheUtil()
        }
    }

    /**
     * 获取缓存大小（MB）
     * */
    fun getCacheSize(activity: Activity):String{
        var finalSize: String
        var cacheSize = activity.cacheDir.size()
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += activity.externalCacheDir!!.size()
        }
        finalSize = getFormatSize(cacheSize)
        return finalSize
    }

    /**
     * 清除缓存
     * */
    fun clearCache(activity: Activity){
         activity.cacheDir.clearFile()
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
           activity.externalCacheDir!!.clearFile()
        }
    }

    /**
     * 转换格式
     * */
    fun getFormatSize(size:Long):String{
        if (size <= 0) {
            return "0.00 KB"
        }
        var kSize = size / 1024f
        if (kSize < 1) {
            var result1 = BigDecimal(kSize.toString())
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB"
        }

        var mSize = kSize / 1024
        var result1 = BigDecimal(mSize.toString())
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB"

    }


    /**
     * 获取文件或者文件夹大小
     * */
    fun File.size():Long{
        var size = 0L
        try {
            if (isFile) {
                size += length()
            }
            if (isDirectory) {
                listFiles().forEach {
                    size += it.size()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 删除文件夹或文件
     * */
    fun File.clearFile() {
        if (isFile) delete()
        if (isDirectory) listFiles().forEach { it.clearFile() }
    }
}
