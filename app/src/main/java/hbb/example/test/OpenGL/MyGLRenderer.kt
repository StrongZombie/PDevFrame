package hbb.example.test.OpenGL

import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import hbb.example.test.OpenGL.Camerautil.CameraUtil
import javax.microedition.khronos.opengles.GL10

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */
class MyGLRenderer : GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    lateinit var triangle: Triangle
    lateinit var square: Square
    /***/
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    private var mSurfaceTexture: SurfaceTexture? = null

    //surface 创建监听
    override fun onSurfaceCreated(unused: GL10, config: javax.microedition.khronos.egl.EGLConfig) {
        // Set the background frame color
        triangle = Triangle()
        // initialize a square
        square = Square()
    }

    // 纹理绘制
    override fun onDrawFrame(unused: GL10) {
        val scratch = FloatArray(16)
        triangle.draw()
        //相机位置

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // Draw shape
        triangle.draw(vPMatrix)


    }

    //     //surface 改变
    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        //投影
        val ratio: Float = width.toFloat() / height.toFloat()
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)

    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {

    }


}