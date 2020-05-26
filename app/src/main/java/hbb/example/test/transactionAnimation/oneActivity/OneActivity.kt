package hbb.example.test.transactionAnimation.oneActivity

import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hbb.example.test.R
import hbb.example.test.customcomponent.ReboundUtil
import hbb.example.test.databinding.OneActivityBinding
import hbb.example.test.transactionAnimation.mainActivity.bean.Sample
import kotlinx.android.synthetic.main.one_activity.*

/**
 * @author HuangJiaHeng
 * @date 2020/2/22.
 */
class OneActivity :AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        val bind:OneActivityBinding = DataBindingUtil.setContentView(this, R.layout.one_activity)
        super.onCreate(savedInstanceState)
        val sample = intent.extras!!.getSerializable("sample") as Sample
        bind.vm =sample
        initAnimation(sample)
        setToolBar()

        ReboundUtil.getUtil(this).addRebound(this,ReboundUtil.NON_SLIING)
    }

    fun initAnimation(sample:Sample){
        window.enterTransition.setDuration(100)

        // Transition for fragment1
        val slideTransition = Slide(Gravity.LEFT)
        slideTransition.setDuration(100)
        // Create fragment and define some of it transitions
        var cb = ChangeBounds()

        val sharedElementFragment1 = OneFragment.newInstance(sample)
        sharedElementFragment1.setReenterTransition(slideTransition)
        sharedElementFragment1.setExitTransition(slideTransition)
        sharedElementFragment1.setSharedElementEnterTransition(cb)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, sharedElementFragment1)
            .commit()
    }

    fun setToolBar(){
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}