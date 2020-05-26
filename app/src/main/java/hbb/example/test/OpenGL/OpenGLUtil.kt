package hbb.example.test.OpenGL

import android.opengl.GLES20
import java.nio.*

/**
 * @author HuangJiaHeng
 * @date 2020/2/17.
 */
object OpenGLUtil {
    /***
     * 编译工具类，生成程序（Int）
     */
    var vertexShaderCode =
        "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}"

    var fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"

    var cameraVertexShaderCode =
    // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
                "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                // the matrix must be included as a modifier of gl_Position
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"




    /**
     * float坐标系
     * */
    private var floatCoords:FloatArray  = floatArrayOf(     // in counterclockwise order:
    0.0f,  0.0f,   0.0f,      // top
    0.0f,  0.0f,  0.0f,     // bottom left
    0.0f,  0.0f, 0.0f      // bottom right  x,y,z的坐标
    )

    /**
     * 设置float坐标系
     * */
    fun setDrawFloatCoords(coords:FloatArray){
        floatCoords = coords
        vertexCount =  floatCoords!!.size / COORDS_PER_VERTEX
    }

    /**
     * 颜色
     * */
    private var color:FloatArray?=null
    fun setDrawColor(color:FloatArray){
        this.color = color
    }

    /***
     * 绘制所需参数
     */
    var mColorHandle: Int = 0
    var positionHandle: Int = 0
    var COORDS_PER_VERTEX = 3

    private var vertexCount: Int = floatCoords!!.size / COORDS_PER_VERTEX
    private var vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    /**
     * 相机投影参数
     * */
    var vPMatrixHandle: Int = 0
    fun init(useX:Boolean):Int {
        var vertexShader: Int
        if (useX){
            vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, cameraVertexShaderCode)
        }else{
            vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        }

        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // create empty OpenGL ES Program
        return GLES20.glCreateProgram().also {

            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }
    }
    /**
     * 编译GLES代码
     * */
    fun loadShader(type: Int, shaderCode: String): Int {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        return GLES20.glCreateShader(type).also { shader ->

            // add the source code to the shader and compile it
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    /**
     * float[] 转 FloatBuffer
     * */
    fun floatArray2FloatBuffer(data:FloatArray):FloatBuffer{
       return  ByteBuffer.allocateDirect(data.size*4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(data)
                position(0)
            }
        }
    }

    /**
     * IntArray 转 IntBuffer
     * */
    fun intArray2IntBuffer(data:IntArray):IntBuffer{
        return  ByteBuffer.allocateDirect(data.size*4).run {
            order(ByteOrder.nativeOrder())
            asIntBuffer().apply {
                put(data)
                position(0)
            }
        }
    }

    /**
     * shoatArray 转 IntBuffer
     * */
    fun shoatArray2ShoatBuffer(data:ShortArray):ShortBuffer{
        return  ByteBuffer.allocateDirect(data.size*2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(data)
                position(0)
            }
        }
    }

    /**
     * 不使用相机与投影进行绘制
     * */
    fun draw(mProgram:Int,vertexBuffer:FloatBuffer){

        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }
    }

    /**
     * 使用相机与投影进行绘制
     * */

    fun drawX(mvpMatrix: FloatArray,mProgram: Int){
        // pass in the calculated transformation matrix

        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}