package hbb.example.test.OpenGL.Camerautil

import android.app.Activity
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import java.util.*

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/6/21
 *   desc  :
 * </pre>
 *
 */
object CameraUtil {
    private var mSurfaceTexture: SurfaceTexture ?= null
    private var mBackgroundThread: HandlerThread? = null
    private var mBackgroundHandler: Handler? = null
    var mContext: Activity ?= null
    fun openCamera(width: Int, height: Int, mSurfaceTexture: SurfaceTexture){
        this.mSurfaceTexture = mSurfaceTexture
        startBackgroundThread()

        //设置预览图像的大小，surfaceview的大小。
        setUpCameraOutputs(width, height)
    }

    private fun setUpCameraOutputs(width: Int, height: Int) {
        if (mContext == null){
            return
        }
//        val manager = mContext!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        try {
//            for (cameraId in manager.cameraIdList) {
//                //相机特性
//                val characteristics = manager.getCameraCharacteristics(cameraId)
//
//                // 后置摄像头 不用
//                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
//                if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
//                    continue
//                }
//                //获取相机支持的可用流配置
//                val map = characteristics.get(
//                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
//                ) ?: continue
//                val displaySize = Point()
//                mContext!!.getWindowManager().getDefaultDisplay().getSize(displaySize)
//                val maxPreviewWidth = displaySize.x
//                val maxPreviewHeight = displaySize.y
////                val largest = Collections.max(
////                    Arrays.asList(map.getOutputSizes(ImageFormat.YUV_420_888)),
////                    CompareSizesByArea()
////                )
//                // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
//                // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
//                // garbage capture data.
//                mPreviewSize = com.example.cameraglrender.util.Camera2Helper.chooseOptimalSize(
//                    map.getOutputSizes(
//                        SurfaceTexture::class.java
//                    ),
//                    width, height, maxPreviewWidth,
//                    maxPreviewHeight, largest
//                )
//                if (onPreviewSizeListener != null) {
//                    onPreviewSizeListener.onSize(mPreviewSize.getWidth(), mPreviewSize.getHeight())
//                }
//                imageReader = ImageReader.newInstance(
//                    mPreviewSize.getWidth(),
//                    mPreviewSize.getHeight(),
//                    ImageFormat.YUV_420_888,
//                    2
//                )
//                imageReader.setOnImageAvailableListener(
//                    mOnImageAvailableListener,
//                    mBackgroundHandler
//                )
//                mCameraId = cameraId
//                return
//            }
//        } catch (e: CameraAccessException) {
//            e.printStackTrace()
//        } catch (e: NullPointerException) {
//        }
    }

    class CompareSizesByArea : Comparator<Size> {

        override fun compare(lhs: Size?, rhs: Size?): Int {
            return java.lang.Long.signum(
                lhs!!.width.toLong() * lhs.height -
                        rhs!!.width.toLong() * rhs.height
            )
        }
    }
    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread?.start()
        mBackgroundHandler = Handler(mBackgroundThread!!.looper)
    }
}