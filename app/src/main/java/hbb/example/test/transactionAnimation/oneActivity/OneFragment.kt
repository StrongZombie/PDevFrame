package hbb.example.test.transactionAnimation.oneActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import hbb.example.test.R
import hbb.example.test.transactionAnimation.mainActivity.bean.Sample
import kotlinx.android.synthetic.main.one_fragment.view.*

/**
 * @author HuangJiaHeng
 * @date 2020/2/24.
 */
class OneFragment :Fragment(){
    companion object{
        fun newInstance(sample:Sample):Fragment{
            val args = Bundle()

            args.putSerializable("sample", sample)
            val fragment = OneFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.one_fragment, container, false)
        val sample = arguments?.getSerializable("sample") as Sample
        view.tv.text = sample.name
        view.iv.background = ContextCompat.getDrawable(context!!,sample.color)
        return view
    }
}