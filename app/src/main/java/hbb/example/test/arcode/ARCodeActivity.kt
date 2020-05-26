package hbb.example.test.arcode

import android.opengl.GLES20
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.ar.core.AugmentedImage
import com.google.ar.core.Frame
import com.google.ar.core.TrackingState
import hbb.example.test.R
import hbb.example.test.algorithm.Algorithm
import hbb.example.test.databinding.ActivityArcodeBinding
import hbb.example.test.slidelayout.FragmentAdapter
import hbb.example.test.slidelayout.SlideLayout
import hbb.example.test.slidelayout.SlideLayout.SlideListener
import kotlinx.android.synthetic.main.activity_arcode.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * @author HuangJiaHeng
 * @date 2020/4/29.
 */
class ARCodeActivity :AppCompatActivity(){

    private lateinit var vm:ARCodeVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBind = DataBindingUtil.setContentView<ActivityArcodeBinding>(this,R.layout.activity_arcode)
        vm = ARCodeVM(this)
        dataBind.vm = vm

        var fragment1  = Fragment(R.layout.activity_animations_scene1)
        var fragment2  = Fragment(R.layout.activity_animations_scene1)

        var adapter = FragmentAdapter(supportFragmentManager, arrayListOf(fragment1,fragment2))

        tab_layout.setupWithViewPager(vp)

        vp.adapter = adapter

        vp.addOnPageChangeListener(SlideLayout.SlideListener())
    }


    override fun onResume() {
        super.onResume()


    }


}