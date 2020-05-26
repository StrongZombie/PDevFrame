package hbb.example.test.view

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import hbb.example.test.Image.PaletteUtil
import hbb.example.test.R
import hbb.example.test.databinding.ActivityMain2Binding
import hbb.example.test.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var vm : MainViewModel

    override fun onClick(v: View) {
        Log.e("tag","onclick")

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBind: ActivityMain2Binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        vm = MainViewModel()
        dataBind.vm  =  vm
        dataBind.isv.setOnClickListener(this)

        PaletteUtil.getPlatteAsync(BitmapFactory.decodeResource(resources,R.drawable.test),
            Palette.PaletteAsyncListener {
                val vibrant = it!!.vibrantSwatch
                // In Kotlin, check for null before accessing properties on the vibrant swatch.
                val titleColor = vibrant?.titleTextColor
                var toolColor = vibrant?.bodyTextColor
                Log.e("tag",titleColor.toString())
                dataBind.tv.setTextColor(it.getLightVibrantColor(Color.RED)!!)
            })

        val slideTransition = Slide()
        slideTransition.slideEdge = Gravity.LEFT
        slideTransition.setDuration(3000)
        window.reenterTransition = slideTransition
        window.exitTransition = slideTransition
        window.enterTransition = slideTransition
    }
}
