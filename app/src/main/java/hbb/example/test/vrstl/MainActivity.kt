package hbb.example.test.vrstl

import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.xuan.gifshow.gif.VrGifView
import com.study.xuan.gifshow.widget.stlview.callback.OnReadCallBack
import com.study.xuan.gifshow.widget.stlview.widget.STLView
import com.study.xuan.gifshow.widget.stlview.widget.STLViewBuilder
import hbb.example.test.databinding.ActGiftStklBinding

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/7/15
 *   desc  :
 * </pre>
 *
 */
class MainActivity: AppCompatActivity() {

    val binding: ActGiftStklBinding by lazy {
        ActGiftStklBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.gif.setTouch(true)
        binding.gif.setDrag(true)
        binding.gif.setScale(false)
        binding.gif.setMoveMode(VrGifView.MODE_FAST)
        val mStl = binding.stl
        mStl.setOnReadCallBack(object : OnReadCallBack() {
            override fun onStart() {
                Toast.makeText(this@MainActivity, "开始解析!", Toast.LENGTH_LONG).show()
//                mBar.show()
            }

            override fun onReading(cur: Int, total: Int) {
//                bundle.putFloat("cur", cur.toFloat())
//                bundle.putFloat("total", total.toFloat())
//                val msg = Message()
//                msg.data = bundle
//                handler.sendMessage(msg)
            }

            override fun onFinish() {
//                mBar.dismiss()
            }
        })
        STLViewBuilder.init(mStl).Assets(this, "bai.stl").build()
        mStl.setTouch(true)
        mStl.setScale(true)
        mStl.setRotate(true)
        mStl.setSensor(false)
    }

}