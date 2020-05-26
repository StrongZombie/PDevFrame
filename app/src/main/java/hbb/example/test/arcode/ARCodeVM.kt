package hbb.example.test.arcode

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import hbb.example.test.aop.anno.ArEnable
import hbb.example.test.aop.anno.CheckArCodeInstall
import hbb.example.test.aop.anno.RequirePermission

import java.io.InputStream


/**
 * @author HuangJiaHeng
 * @date 2020/4/29.
 */
class ARCodeVM(var activity: Activity) {

    var mSession:Session?=null
    var index = 0

    companion object{
        fun maybeEnableArButton(activity: Context):Boolean{
            val availability = ArCoreApk.getInstance().checkAvailability(activity)
            if (availability.isTransient) { // Re-query at 5Hz while compatibility is checked in the background.
                Handler().postDelayed(Runnable { maybeEnableArButton(activity) }, 200)
            }
            return availability.isSupported
        }
    }



    @RequirePermission([android.Manifest.permission.CAMERA])
    @ArEnable
    @CheckArCodeInstall
    fun mayBeEnable(){

        var imageDatabase:AugmentedImageDatabase =  AugmentedImageDatabase(mSession)

        var isp:InputStream = activity.assets.open("image/jjj.jpg")

        var bitmap = BitmapFactory.decodeStream(isp)

        index = imageDatabase.addImage("jjj", bitmap, 2f)

        // Create default config and check if supported.
        val config = Config(mSession)
        config.augmentedImageDatabase = imageDatabase
        mSession!!.configure(config)



    }

}