package hbb.example.test.slidelayout

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

/**
 * @author HuangJiaHeng
 * @date 2020/5/18.
 */
class SlideLayout(var mContext: Context): TabLayout(mContext){

     class SlideListener(): ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            Log.e("tag","position:$position positionSet:$positionOffset positionPixel:$positionOffsetPixels")
        }

        override fun onPageSelected(position: Int) {

        }

    }
}