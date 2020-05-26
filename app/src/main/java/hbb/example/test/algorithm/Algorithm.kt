package hbb.example.test.algorithm

import android.util.Log

/**
 * @author HuangJiaHeng
 * @date 2020/3/16.
 */
class Algorithm {

    var data = arrayListOf(32,55,32,-1,23,0,5)

    /**
     * O(n^2)
     * 冒泡排序
     * 循环让每个值同右边的值比较，大的互换，小于不操作，每次循环都能找出最右边的最大值
     * 因此每次循环需要减少已循环的次数
     * */
    fun bubbleSort(){

        for (j in 0 until data.size){
            var flag = false
            for ( j in 0 until data.size-j-1){

                if (data[j]>data[j+1]){
                    var temp = data[j]
                    data[j] = data[j+1]
                    data[j+1] = temp
                    flag = true
                }

            }
            if (!flag) break  //如果执行第一趟没有需要转换的值证明已经是排好序的数组，直接退出
        }
        show();
    }

    /**
     * O(n^2)
     * 选择排序，
     * 选择最大值(或者最小值)体会尾（头）
     * */
    fun selectionSort(){
        data = arrayListOf(32,55,32,-1,23,0,5)
        for (j in 0 until data.size){
            var max = data[0]
            var maxIndex = 0
            for (i in 1 until data.size-j){

                if (max<data[i]){
                    max = data[i]
                    maxIndex = i
                }

            }
            data[maxIndex] = data[data.size-j-1]
            data[data.size-j-1] = max
        }
        show()
    }

    /**
     * 插入排序
     * O（n^2）
     * 第一次：拿数组第一个元素，从左边拿一个值，比较这个元素的大小，进行交互
     * 第二次：已排好的2个数，继续从左边拿一个值，比较排好两个数，看插入到哪里
     * 如果数据序列基本有序，使用插入排序会更加高效。
     * */
    fun insertionSort(){

        var temp = 0
        for (i in 0 until data.size-1){
            for (j in i+1 downTo 1){
                if (data[j]>data[j-1]){
                    temp = data[j]
                    data[j] = data[j-1]
                    data[j-1]= temp
                }
            }



        }
        show()
    }

    fun insertionSortWhile(){
        var tmp= 0
        var j = 0
        for (i in 0 until data.size-1){
            tmp = data[i+1]
            j = i+1
            while (j>=1 && tmp<data[j-1]){
                data[j-1] = data[j]
                j--
            }
            data[j] =tmp

        }
        show()
    }

    /**
     * 希尔排序
     * 让增量等于数组长度的一半
     * 根据增量把数组分为几部分，0，0+gap一组  1，1+gap一组 。。。
     * 当增量减少时 0，0+gap,0+gap+gap 为一组，进行插入排序，只要小于总长就行
     * 对这些组进行插入排序
     * */
    fun shellSort(){
        var gap = data.size/2
        while (gap<=1){

            for (i in gap until data.size-1){

                //进行0，0+gap一组  1，1+gap的插入排序
                var temp = data[i]
                var j = i-gap
                while (j>0 && temp<data[j]){
                    data[j+gap] = data[j]
                    j -= gap
                }
                data[j + gap] = temp
            }

        }

    }

    /**
     * O(N*logN)
     * 快速排序
     * 找到一个基准（任意值）以这个基准，
     * 让j从右到左找比基准值小的值，i从左到右找大值
     * 符合就想换，没找到，就当i=j时交换基准和i所处位置的值
     * 在交换后基准值的左右两个数组中重复上面的操作
     * 一定要从右到左
     * 假如从左开始
     * 6 1 2 7 9
     * i 就会移动到现在的 数字 7 那个位置停下来，而  j 原来在 数字 9 那个位置
     * 于是，j 也会停留在数字7 那个位置，然后 i == j了，
     * */
    fun quicekSort(data:ArrayList<Int>,low:Int,height:Int):ArrayList<Int>{

        if (data == null || data.size<=1 || low>height){
            return data
        }
        //拿基准
        var target = data[low]
        var i = low
        var j = height

        while (i<j){
            while (j>i && data[j]>target){
                j--
            }
            while (i<j && data[i]<target){
                i++
            }
            if (i<j){
                var k = data[j]
                data[j] = data[i]
                data[i] = k
            }
        }


        data[low] = data[i]
        data[i] = target

        var sort = arrayListOf<Int>()
        sort.addAll(quicekSort(data,low,i-1))
        sort.add(target)
        sort.addAll(quicekSort(data,i+1,height))
        return sort


    }

    /**
     * 归并排序
     * 将 a b 数组放到c中
     * 哪个小先放哪个
     * 可把数组分两段进行归并
     * */
    fun memerySort(){
        var a = intArrayOf(3,4,5,6)
        var b = intArrayOf(34,16,84)
        var c = IntArray(7)
        var i: Int
        var j: Int
        var k: Int = 0
        var n = a.size
        var m = b.size
        i = 0.also { k = it }.also { j = it }
//        while (i < n && j < m) {
//            if (a.get(i) < b.get(j)) c.get(k++) = a.get(i++) else c.get(k++) = b.get(j++)
//        }
//
//        while (i < n) c.get(k++) = a.get(i++)
//
//        while (j < m) c.get(k++) = b.get(j++)
    }


    private fun show(){
        Log.e("Algorithm","排序结果：")
        data.forEach { i ->
            Log.e("Algorithm",i.toString())
        }
    }
    private fun show(arr:ArrayList<Int>){
        Log.e("Algorithm","排序结果：")
        arr.forEach { i ->
            Log.e("Algorithm",i.toString())
        }
    }
}