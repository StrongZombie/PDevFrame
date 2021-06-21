package hbb.example.test.bilibili;

import android.util.Log;

import java.util.ArrayList;

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/4/21
 *   desc  :
 * </pre>
 */
public class Solution {
    /**
     * 黄金投资
     * @param prices int整型一维数组 价格
     * @return int整型
     */
    public static int maxProfit (int[] prices) {
        if (prices.length <=1){
            return 0;
        }
        ArrayList<Integer>up = new ArrayList<>();
        up.add(0);
        for (int i = 0; i<prices.length-1; i++){
            if (prices[i+1] < prices[i]){
                up.add(i);
            }
        }
        up.add(prices.length-1);
        int [] max = new int[prices.length];
        for (int i = 1;i<up.size();i++){
            max[i] = prices[up.get(i)] - prices[up.get(i-1)==0?0:up.get(i-1)+1];
        }
        int a =0,b = 0;
        for (int i = 0; i<max.length;i++){
            if (max[i]==a){
                b=a;
            }
            if (max[i]>a){
                b = a;
                a=max[i];
            }
            if (max[i] > b && max[i] < a){
                b=max[i];
            }

        }
        return a+b;
    }

    public static float calcShunzi(){
        char[] puke = new char[52];
        for (int i=1;i<=52;i++){
            int p = i%13;
            if (p<=10 && p>=1){
//                puke.ad
            }
        }
        return  0f;
    }


}
