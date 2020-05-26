package hbb.example.test.transactionAnimation.twoactivity

import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene

import android.transition.TransitionInflater
import android.transition.TransitionManager
import androidx.annotation.RequiresApi


import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


import hbb.example.test.R
import hbb.example.test.databinding.TwoActivityBinding
import kotlinx.android.synthetic.main.two_activity.*

/**
 * @author HuangJiaHeng
 * @date 2020/2/24.
 */
class TwoActivity :AppCompatActivity(){

    lateinit var scene1 : Scene
    lateinit var scene2 : Scene
    lateinit var scene3 :Scene
    lateinit var scene4 :Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        val bind = DataBindingUtil.setContentView<TwoActivityBinding>(this, R.layout.two_activity)
        super.onCreate(savedInstanceState)

        initScene()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun initScene(){
        scene1 = Scene.getSceneForLayout(scene_root,R.layout.activity_animations_scene1,this)
        scene2 = Scene.getSceneForLayout(scene_root,R.layout.activity_animations_scene2,this)
        scene3 = Scene.getSceneForLayout(scene_root,R.layout.activity_animations_scene3,this)
        scene4 = Scene.getSceneForLayout(scene_root,R.layout.activity_animations_scene4,this)

        TransitionManager.beginDelayedTransition(scene_root)
        btn1.setOnClickListener {
            TransitionManager.go(scene1, ChangeBounds())
        }
        btn2.setOnClickListener {
            TransitionManager.go(scene2,TransitionInflater.from(this).inflateTransition(R.transition.test))
        }
        btn3.setOnClickListener {
            TransitionManager.go(scene3,ChangeBounds())
        }
        btn4.setOnClickListener {
            TransitionManager.go(scene4,ChangeBounds())
        }

    }
}