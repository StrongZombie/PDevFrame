package hbb.example.test.ipc

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import hbb.example.test.App

/**
 * <pre>
 *   author: hjh
 *   time  : 2020/12/18
 *   desc  : 单线程IPC
 * </pre>
 *
 */
class Messenger : Service() {

    val messenger:Messenger

    init {
        messenger = Messenger(MessageBinder())
    }

    class MessageBinder : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what){
                1001 -> Toast.makeText(App.app, "收到！！！", Toast.LENGTH_SHORT).show()
            }
            super.handleMessage(msg)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return messenger.binder
    }
}