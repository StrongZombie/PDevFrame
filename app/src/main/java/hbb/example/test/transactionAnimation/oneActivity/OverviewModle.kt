package hbb.example.test.transactionAnimation.oneActivity

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import hbb.example.test.R

/**
 * @author HuangJiaHeng
 * @date 2020/2/22.
 */
class OverviewModle{
    var name:ObservableField<String> = ObservableField("123")
    var drawable:ObservableInt = ObservableInt(R.drawable.red_circle)
}