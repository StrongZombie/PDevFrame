package hbb.example.test.glide

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hbb.example.test.R
import hbb.example.test.databinding.ActGliderMainBinding

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/7/26
 *   desc  :
 * </pre>
 *
 */
class MainActivity : AppCompatActivity(){

    val binding: ActGliderMainBinding by lazy {
        ActGliderMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        GlideApp.with(this).load(R.drawable.a2).miniThumb().into(binding.iv)
    }
}