package hbb.example.test.vpn

import android.app.*
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.IBinder
import hbb.example.test.R
import hbb.example.test.recyclerview.MainActivity


/**
 * <pre>
 *   author: hjh
 *   time  : 2021/6/24
 *   desc  :
 * </pre>
 *
 */
class VpnForceService: VpnService() {

    companion object{
        const val VPN_CHANNEL = "一元手游"
        const val VPN_CHANNEL_ID = 1
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "一元手游"
            val descriptionText = "一元手游加速服务"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(VPN_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            Notification.Builder(this, VPN_CHANNEL)
        } else {
            Notification.Builder(this)
        }
        builder.setContentTitle("一元手游加速器")
                .setContentText("加速器持续加速中")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setTicker("一元手游")
        startForeground(VPN_CHANNEL_ID, builder.build())
    }
}