package hbb.example.test.OpenGL

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */
class MyGLSurfaceView: GLSurfaceView {

    @JvmOverloads
    constructor(context: Context,attributeSet: AttributeSet?=null):super(context,attributeSet)
    private val renderer: MyGLRenderer =
        MyGLRenderer()

    init {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}