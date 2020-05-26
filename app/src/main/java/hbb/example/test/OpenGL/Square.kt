package hbb.example.test.OpenGL

import hbb.example.test.OpenGL.OpenGLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */


class Square {
    var squareCoords = floatArrayOf(
        -0.5f,0.5f,0.0f,
        0.5f,0.5f,0.0f,
        -0.5f,-0.5f,0.0f,
        0.5f,-0.5f,0.0f
    )
    // Set color with red, green, blue and alpha (opacity) values

    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)  //颜色
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices
    private var mProgram:Int = OpenGLUtil.init(false)
    init {
        OpenGLUtil.setDrawColor(color)
        OpenGLUtil.setDrawFloatCoords(squareCoords)
    }

    fun draw(){
        OpenGLUtil.draw(mProgram,OpenGLUtil.floatArray2FloatBuffer(squareCoords))
    }
}
