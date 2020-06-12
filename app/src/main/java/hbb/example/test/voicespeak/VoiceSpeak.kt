package hbb.example.test.voicespeak

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.util.ArrayMap
import android.util.Log
import hbb.example.test.R
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

/**
 * @author HuangJiaHeng
 * @date 2020/6/5.
 * 金额语音播报类

 * playVoicePool SoundPool 可以有效防止卡顿
 */
class VoiceSpeak {

    /**
     * SoundPool 使用SoundPool方式才初始化
     * */
    private lateinit var mSoundPool:SoundPool
    /**
     * SoundPool ID ArrayMap
     * 数据量小使用ArrayMap比HashMap性能更好
     * */
    private var soundId = ArrayMap<String,Int>()
    /**
     * SoundPool是否加载完成
     * */
    private var isPrepare = false

    companion object{

        private val NUM = arrayListOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        private val CHIN_NUM = arrayListOf('元', '拾', '佰', '仟', '万', '亿','点')

        val util by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            VoiceSpeak()
        }
    }

    /**
     * 释放资源
     * */
    fun destroyPool(){
        if (mSoundPool!=null){
            mSoundPool.autoPause()
            mSoundPool.release()
            mSoundPool == null
        }
    }

    /**
     * 以池的方式播放，可以有效减少资源解码卡顿情况 (同步锁)
     * @param moneyString 播报的金额中文串（通过MoneyUtil获得）
     * @param context
     * */
    fun playVoicePool(moneyString: String,context: Context) {
        thread {
            synchronized(this){
                if (isPrepare){
                    var soundList = getListSound(moneyString)
                    for (i in soundList){
                        if (soundId[i]!=null){
                            mSoundPool.play(soundId[i]!!,1f,1f,1,0,1f)
                            if (i.contains("tts_success")){
                                Thread.sleep(1100)
                            }else{
                                Thread.sleep(400)
                            }
                        }
                    }
                }else{
                    init(context,moneyString,true)
                }

            }
        }
    }

    /**
     * 以MediaPlayer 方式播报，语音播放间隔需要解码（性能差的手机间隔大）时间不可控
     * @param moneyString 播报的金额中文串（通过MoneyUtil获得）
     * @param context
     * */
    @Deprecated("以MediaPlayer 方式播报，语音播放间隔需要解码（性能差的手机间隔大）时间不可控")
    fun playVoice(moneyString: String,context: Context){
        thread {
            synchronized(this){
                var soundList = getListSound(moneyString)
                var countDownLatch = CountDownLatch(1)
                val mMediaPlayer = MediaPlayer()

                var assetFileDescription: AssetFileDescriptor? = null
                var playIndex = 0
                assetFileDescription = context.assets.openFd(soundList[playIndex])
                mMediaPlayer.setDataSource(assetFileDescription)
                mMediaPlayer.prepareAsync()
                mMediaPlayer.setOnPreparedListener {
                    Log.e("moneyUtil","播放")
                    it.start()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (it.isPlaying) {
                            it.playbackParams = it.playbackParams.setSpeed(1.6.toFloat())
                        }
                    }
                }
                mMediaPlayer.setOnCompletionListener {
                    Log.e("moneyUtil","解析")
                    it.reset()
                    playIndex++
                    if (playIndex < soundList.size) {
                        var fileDescription = context.assets.openFd(soundList[playIndex])
                        mMediaPlayer.setDataSource(fileDescription)
                        mMediaPlayer.prepareAsync()
                    } else {
                        it.release()
                        countDownLatch.countDown()
                    }
                }

                countDownLatch.await()
            }
        }
    }


    /**
     * 根据@link MoneyUtil生成的中文串来获取链表
     * @param moneyString 播报的金额中文串（通过MoneyUtil获得）
     * @return
     * */
    private fun getListSound(moneyString: String):LinkedList<String>{
        var soundList = LinkedList<String>()
        var moneyChar = moneyString.toCharArray()

        soundList.add("sound/tts_success.mp3")

        for (i in moneyChar){
            if (NUM.contains(i)){
                soundList.add(getStringFormNUM(i))
            }
            if (CHIN_NUM.contains(i)){
                soundList.add(getStringFormCHINNUM(i))
            }
        }
        return soundList
    }

    /**
     * 获取中文字符资源
     * @param char
     * */
    private fun getStringFormCHINNUM(char: Char):String{
        var chineseNumPath =""
        when(char){
            '元' -> chineseNumPath = "sound/tts_yuan.mp3"
            '拾' -> chineseNumPath = "sound/tts_ten.mp3"
            '佰' -> chineseNumPath = "sound/tts_hundred.mp3"
            '仟' -> chineseNumPath = "sound/tts_thousand.mp3"
            '万' -> chineseNumPath = "sound/tts_ten_thousand.mp3"
            '亿' -> chineseNumPath = "sound/tts_ten_million.mp3"
            '点' -> chineseNumPath = "sound/tts_dot.mp3"
        }
        return chineseNumPath
    }

    /**
     * 获取数字资源
     * @param char
     * */
    private fun getStringFormNUM(char:Char):String{
        return String.format("sound/tts_%s.mp3",char)
    }

    /**
     * 初始化，第一次使用时自动初始化，无需手动调用
     * @param context
     * @param moneyString 播报的金额中文串（通过MoneyUtil获得）
     * @param autoPlay 初始化后是否自动播放
     * */
    private fun init(context: Context,moneyString:String ="",autoPlay:Boolean=false){

        var audioAttributes =  AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();
        var assets = context.assets
        mSoundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(22).build()

        mSoundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0 &&sampleId == soundId.size){
                isPrepare = true
                if (autoPlay){
                    playVoicePool(moneyString,context)
                }
            }
        }
        soundId.clear()
        soundId["sound/tts_1.mp3"] = mSoundPool.load(assets.openFd("sound/tts_1.mp3"),1)
        soundId["sound/tts_2.mp3"] = mSoundPool.load(assets.openFd("sound/tts_2.mp3"),1)
        soundId["sound/tts_3.mp3"] = mSoundPool.load(assets.openFd("sound/tts_3.mp3"),1)
        soundId["sound/tts_4.mp3"] = mSoundPool.load(assets.openFd("sound/tts_4.mp3"),1)
        soundId["sound/tts_5.mp3"] = mSoundPool.load(assets.openFd("sound/tts_5.mp3"),1)
        soundId["sound/tts_6.mp3"] = mSoundPool.load(assets.openFd("sound/tts_6.mp3"),1)
        soundId["sound/tts_7.mp3"] = mSoundPool.load(assets.openFd("sound/tts_7.mp3"),1)
        soundId["sound/tts_8.mp3"] = mSoundPool.load(assets.openFd("sound/tts_8.mp3"),1)
        soundId["sound/tts_9.mp3"] = mSoundPool.load(assets.openFd("sound/tts_9.mp3"),1)
        soundId["sound/tts_0.mp3"] = mSoundPool.load(assets.openFd("sound/tts_0.mp3"),1)
        soundId["sound/tts_dot.mp3"] = mSoundPool.load(assets.openFd("sound/tts_dot.mp3"),1)
        soundId["sound/tts_hundred.mp3"] = mSoundPool.load(assets.openFd("sound/tts_hundred.mp3"),1)
        soundId["sound/tts_success.mp3"] = mSoundPool.load(assets.openFd("sound/tts_success.mp3"),1)
        soundId["sound/tts_ten_million.mp3"] = mSoundPool.load(assets.openFd("sound/tts_ten_million.mp3"),1)
        soundId["sound/tts_ten_thousand.mp3"] = mSoundPool.load(assets.openFd("sound/tts_ten_thousand.mp3"),1)
        soundId["sound/tts_ten.mp3"] = mSoundPool.load(assets.openFd("sound/tts_ten.mp3"),1)
        soundId["sound/tts_thousand.mp3"] = mSoundPool.load(assets.openFd("sound/tts_thousand.mp3"),1)
        soundId["sound/tts_yuan.mp3"] = mSoundPool.load(assets.openFd("sound/tts_yuan.mp3"),1)
    }
}
