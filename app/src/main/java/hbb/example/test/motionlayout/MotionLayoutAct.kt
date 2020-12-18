package hbb.example.test.motionlayout

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import hbb.example.test.databinding.ActMotionlayouBinding

/**
 * <pre>
 *   author: hjh
 *   time  : 2020/12/11
 *   desc  :
 * </pre>
 *
 */
class MotionLayoutAct :AppCompatActivity() {

    private val actMotionlayouBinding:ActMotionlayouBinding by lazy {
        ActMotionlayouBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(actMotionlayouBinding.root)
        super.onCreate(savedInstanceState)
    }
}