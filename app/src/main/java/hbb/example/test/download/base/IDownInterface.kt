package hbb.example.test.download.base

/**
 * Created by hjh on 2020/8/5
 */
interface IDownInterface {

    fun onProgress(total:Long,progress:Long,speed:Int)

    fun onDownFinish()

    fun onDownFail(message:String)

    fun onDownStop()

    fun onDownPause()

}