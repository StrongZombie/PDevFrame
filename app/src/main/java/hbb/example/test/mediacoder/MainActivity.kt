package hbb.example.test.mediacoder

import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.media.MediaBrowserServiceCompat
import hbb.example.test.R
import kotlinx.android.synthetic.main.mediacoder_activity.*

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/4/8
 *   desc  :
 * </pre>
 *
 */
class MainActivity: AppCompatActivity() {
    private lateinit var mediaSession: MediaSessionCompat
    private val LOG_TAG = "123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mediacoder_activity)
        btn.setOnClickListener {

        }
    }

    private fun MySessionCallback() = object: MediaSessionCompat.Callback(){
        override fun onPause() {
            super.onPause()
        }

        override fun onStop() {
            super.onStop()
        }
    }
}
