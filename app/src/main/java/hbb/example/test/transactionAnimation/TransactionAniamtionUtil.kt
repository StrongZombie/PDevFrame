package hbb.example.test.transactionAnimation

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair

/**
 * @author HuangJiaHeng
 * @date 2020/2/22.
 */
class TransactionAniamtionUtil {
    companion object{
        private val util:TransactionAniamtionUtil by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            TransactionAniamtionUtil()
        }
    }
    fun skipWithAnimation(pairs:Pair<View,String>,target:Class<Any>,activity:Activity){
        val i = Intent(activity, target)
        val transitionActivityOptions =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs)
        activity.startActivity(i, transitionActivityOptions.toBundle())
    }
}