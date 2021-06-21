package hbb.example.test.eventmanager.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import hbb.example.test.eventmanager.Subscribe

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/1/15
 *   desc  :
 * </pre>
 *
 */
open class A : AppCompatActivity() {
    @Subscribe(ThreadMode = Subscribe.MAIN_THREAD)
    fun sss(){

    }
}