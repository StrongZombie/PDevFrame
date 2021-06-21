package hbb.example.test.OpenGL

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import hbb.example.test.view.MainActivity

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */
class GLSurfaceActivity :AppCompatActivity(){
    private lateinit var gLView: MyGLSurfaceView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = MyGLSurfaceView(this)
        setContentView(gLView)

//        gLView.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//        val slideTransition = Slide()
//        slideTransition.slideEdge = Gravity.LEFT
//        slideTransition.setDuration(3000)
//        window.reenterTransition = slideTransition
//        window.exitTransition = slideTransition
    }
}