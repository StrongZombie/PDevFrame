package hbb.example.test.mediacoder

import android.media.MediaCodec
import android.media.MediaFormat
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ShortBuffer

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/4/9
 *   desc  :
 * </pre>
 *
 */
class MediacoderGuilde {

    companion object{
        val INSTANCE: MediacoderGuilde by  lazy {
            MediacoderGuilde()
        }
    }

    fun startCoder(){
//        MediaCodec.createByCodecName()
    }

    // Assumes the buffer PCM encoding is 16 bit.
    fun getSamplesForChannel(codec: MediaCodec, bufferId: Int, channelIx: Int): ShortArray? {
        val outputBuffer: ByteBuffer? = codec.getOutputBuffer(bufferId)
        val format: MediaFormat = codec.getOutputFormat(bufferId)
        val samples: ShortBuffer = outputBuffer?.order(ByteOrder.nativeOrder())!!.asShortBuffer()
        val numChannels: Int = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
        if (channelIx < 0 || channelIx >= numChannels) {
            return null
        }
        val res = ShortArray(samples.remaining() / numChannels)
        for (i in res.indices) {
            res[i] = samples.get(i * numChannels + channelIx)
        }
        return res
    }
}