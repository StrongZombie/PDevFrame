package hbb.example.test.Image

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

/**
 * @author HuangJiaHeng
 * @date 2020/2/8.
 */
class PaletteUtil {
    companion object {
        private lateinit var pattle: PaletteUtil

        fun getPlatte(
            bitmap: Bitmap
        ): Palette {
            return Palette.from(bitmap).generate()
        }

        fun getPlatteAsync(bitmap: Bitmap,listener:Palette.PaletteAsyncListener) {
            Palette.from(bitmap).generate (listener)
        }
    }
}