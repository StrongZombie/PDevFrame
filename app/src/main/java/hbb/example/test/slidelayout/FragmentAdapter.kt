package hbb.example.test.slidelayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author HuangJiaHeng
 * @date 2020/5/18.
 */
class FragmentAdapter (fm:FragmentManager,var view:ArrayList<Fragment>):FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return view[position]
    }

    override fun getCount(): Int {
        return view.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "title"
    }

}