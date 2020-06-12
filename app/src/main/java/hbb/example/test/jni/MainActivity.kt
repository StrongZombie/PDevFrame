package hbb.example.test.jni

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.firebase.FirebaseApp
import hbb.example.test.R
import hbb.example.test.voicespeak.MoneyUtil
import hbb.example.test.voicespeak.VoiceSpeak
import kotlinx.android.synthetic.main.activity_jni.*
import primary.student.home.MainActivity
import java.util.*


/**
 * @author HuangJiaHeng
 * @date 2020/6/3.
 */
@Route (path = "/app/main")
class MainActivity :AppCompatActivity(){

    external fun ffmpegInfo(): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("main")
        }
    }

    lateinit var textToSpeech:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        FirebaseApp.initializeApp(this)
        ARouter.getInstance().inject(this)
//        Log.e("MoneyUtil",FirebaseInstanceId.getInstance().id+"\t"+FirebaseInstanceId.getInstance().instanceId)
        tv_from_jni.text = ffmpegInfo()

        tv_from_jni.setOnClickListener {
//            startActivity(Intent(this,MainActivity::class.java))
            ARouter.getInstance().build("/home/main").navigation()
        }
        textToSpeech = TextToSpeech(this,TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS){
                textToSpeech.language = Locale.CHINESE
                textToSpeech .speak("收款"+MoneyUtil.readString("30.57"),TextToSpeech.QUEUE_FLUSH,null,"1")
            }
        })

        Log.e("MoneyUtil",MoneyUtil.readString("30.57"))
        Log.e("MoneyUtil",MoneyUtil.readString("108"))
        Log.e("MoneyUtil",MoneyUtil.readString("556.78"))
        Log.e("MoneyUtil",MoneyUtil.readString("104850000.56"))
        Log.e("MoneyUtil",MoneyUtil.readString("10090005000"))
        Log.e("MoneyUtil",MoneyUtil.readString("111111111.11"))
//        Log.e("MoneyUtil",MoneyUtil.readString("30.57"))
//        Log.e("MoneyUtil",MoneyUtil.readString("30.57"))
        VoiceSpeak.util.playVoicePool(MoneyUtil.readString("30.57"),this)
        VoiceSpeak.util.playVoicePool(MoneyUtil.readString("108"),this)
        VoiceSpeak.util.playVoicePool(MoneyUtil.readString("556.78"),this)
        VoiceSpeak.util.playVoicePool(MoneyUtil.readString("104850000.56"),this)
        VoiceSpeak.util.playVoicePool(MoneyUtil.readString("10090005000"),this)
        VoiceSpeak.util.playVoicePool(MoneyUtil.readString("111111111.11"),this)

    }

}