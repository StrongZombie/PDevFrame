package hbb.example.test.OpenGL

import android.opengl.GLES20

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */

class Triangle {
    /**
     * 编译程序
     * */
    var mProgram: Int = OpenGLUtil.init(false)

    /**
     * 颜色和坐标系buffer
     * */
    private var triangleCoords = floatArrayOf(     // in counterclockwise order:
        0.0f, 0.622008459f, 0.0f,      // top
        -0.5f, -0.311004243f, 0.0f,    // bottom left
        0.5f, -0.311004243f, 0.0f      // bottom right  x,y,z的坐标
    )
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)  //颜色

    init {
        OpenGLUtil.setDrawFloatCoords(triangleCoords)
        OpenGLUtil.setDrawColor(color)
    }

    fun draw() {
        OpenGLUtil.draw(mProgram,OpenGLUtil.floatArray2FloatBuffer(triangleCoords))
    }
    var mColorHandle: Int = 0
    var positionHandle: Int = 0
    var COORDS_PER_VERTEX = 3

    private var vertexCount: Int =triangleCoords.size / COORDS_PER_VERTEX
    private var vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    fun draw1(){
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        OpenGLUtil.positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(
                it,
                OpenGLUtil.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                OpenGLUtil.floatArray2FloatBuffer(triangleCoords)
            )

            // get handle to fragment shader's vColor member
            OpenGLUtil.mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1,color, 0)
            }

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }
    }
    /***
     * 应用相机投影的绘画
     */
    private var vPMatrixHandle: Int = 0
    fun draw(mvpMatrix: FloatArray) { // pass in the calculated transformation matrix
        OpenGLUtil.drawX(mvpMatrix,mProgram)
    }

}
