package hbb.example.test

import android.util.Log
import org.junit.Test

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/4/20
 *   desc  :
 * </pre>
 *
 */

object ZhenTi {

    const val doCount = 2
    @Test
    fun calcLr() {
        val list = intArrayOf(1,2,8,7)
        if (list.size >= doCount * 2){
            var l = 0
            for(i in list.indices){ //1买
                for (j in list.size - i .. list.size){ //2卖
                    for (q in list.size - j..list.size){ //3买
                        var l1 = 0
                        for (z in list.size - q..list.size){ //4卖
                            l1 = (z - q) + (j - i)
                            if (l1>l){
                                l = l1
                            }
                        }
                    }
                }
            }
            Log.e("ZhenTi","LIR:"+l)
        }else{ //小于4个元素
            var l = 0
            for (i in list.indices){
                var l1 = 0
                for (j in list.size - i .. list.size){
                    l1 = j - i
                    if (l1 > l){
                        l = l1
                    }
                }
            }
            Log.e("ZhenTi","LIR:"+l)
        }
    }
}