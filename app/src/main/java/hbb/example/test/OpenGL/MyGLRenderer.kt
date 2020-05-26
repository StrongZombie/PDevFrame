package hbb.example.test.OpenGL

import android.opengl.*
import javax.microedition.khronos.opengles.GL10

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */
class MyGLRenderer : GLSurfaceView.Renderer {
    lateinit var triangle: Triangle
    lateinit var square: Square
    /***/
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    override fun onSurfaceCreated(unused: GL10, config: javax.microedition.khronos.egl.EGLConfig) {
        triangle = Triangle()
        square = Square()

    }

    override fun onDrawFrame(unused: GL10) {
//        triangle.draw()
        square.draw()
//        // Set the camera position (View matrix)
//        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
//
//        // Calculate the projection and view transformation
//        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
//
//


    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
//        GLES20.glViewport(0, 0, width, height)
//
//        val ratio: Float = width.toFloat() / height.toFloat()
//
//        // this projection matrix is applied to object coordinates
//        // in the onDrawFrame() method
//        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
    }


}