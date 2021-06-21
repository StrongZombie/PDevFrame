package hbb.example.test.OpenGL.Camerautil

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
    fun openCamera(width: Int, height: Int, mSurfaceTexture: SurfaceTexture){
        this.mSurfaceTexture = mSurfaceTexture
        startBackgroundThread()

        //设置预览图像的大小，surfaceview的大小。
        setUpCameraOutputs(width, height)
    }

    private fun setUpCameraOutputs(width: Int, height: Int) {
        val manager = mContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (cameraId in manager.cameraIdList) {
                val characteristics = manager.getCameraCharacteristics(cameraId)

                // We don't use a front facing camera in this sample.
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
                    continue
                }
                val map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
                )
                    ?: continue
                val displaySize = Point()
                mContext.getWindowManager().getDefaultDisplay().getSize(displaySize)
                val maxPreviewWidth = displaySize.x
                val maxPreviewHeight = displaySize.y
                val largest = Collections.max(
                    Arrays.asList(*map.getOutputSizes(ImageFormat.YUV_420_888)),
                    com.example.cameraglrender.util.Camera2Helper.CompareSizesByArea()
                )
                // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
                // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
                // garbage capture data.
                mPreviewSize = com.example.cameraglrender.util.Camera2Helper.chooseOptimalSize(
                    map.getOutputSizes(
                        SurfaceTexture::class.java
                    ),
                    width, height, maxPreviewWidth,
                    maxPreviewHeight, largest
                )
                if (onPreviewSizeListener != null) {
                    onPreviewSizeListener.onSize(mPreviewSize.getWidth(), mPreviewSize.getHeight())
                }
                imageReader = ImageReader.newInstance(
                    mPreviewSize.getWidth(),
                    mPreviewSize.getHeight(),
                    ImageFormat.YUV_420_888,
                    2
                )
                imageReader.setOnImageAvailableListener(
                    mOnImageAvailableListener,
                    mBackgroundHandler
                )
                mCameraId = cameraId
                return
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
        }
    }

    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread?.start()
        mBackgroundHandler = Handler(mBackgroundThread!!.looper)
    }
}