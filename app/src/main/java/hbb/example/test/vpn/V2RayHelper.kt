package hbb.example.test.vpn

import android.content.Context
import java.io.*

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/6/25
 *   desc  :
 * </pre>
 *
 */
object V2RayHelper {
    var application: Context?= null
    var dataPath =  application?.filesDir?.absolutePath

    /**
     * 更新 域文件 dat
     * ip信息库吧，比如配置了只允许访问中国的ip
     * */
    fun initGTP(){
        try {
            val file = File(dataPath, "geoip.dat")
            val file1 = File(dataPath, "geosite.dat")
            if (!file.exists()) {
                copyFileUsingFileChannels(
                    application!!.assets.open("geoip.dat"), file
                )
            }
            if (!file1.exists()) {
                copyFileUsingFileChannels(
                    application!!.assets.open("geosite.dat"), file1
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {

        }
    }

    fun copyFileUsingFileChannels(source: InputStream, dest: File?) {
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(dest)
            val bytes = ByteArray(1024 * 5)
            var read: Int
            while (source.read(bytes).also { read = it } != -1) {
                outputStream.write(bytes, 0, read)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            close(source, outputStream)
        }
    }

    fun close(vararg closeables: Closeable?) {

        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                }
            }
        }
    }
}