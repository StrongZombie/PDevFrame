package hbb.example.test.algorithm

import android.util.Log

/**
 * @author HuangJiaHeng
 * @date 2020/3/16.
 */
class Algorithm {

    var data = arrayListOf(32, 55, 32, -1, 23, 0, 5)

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

                if (data[j]>data[j + 1]){
                    var temp = data[j]
                    data[j] = data[j + 1]
                    data[j + 1] = temp
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
        data = arrayListOf(32, 55, 32, -1, 23, 0, 5)
        for (j in 0 until data.size){
            var max = data[0]
            var maxIndex = 0
            for (i in 1 until data.size-j){

                if (max<data[i]){
                    max = data[i]
                    maxIndex = i
                }

            }
            data[maxIndex] = data[data.size - j - 1]
            data[data.size - j - 1] = max
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
                if (data[j]>data[j - 1]){
                    temp = data[j]
                    data[j] = data[j - 1]
                    data[j - 1]= temp
                }
            }



        }
        show()
    }

    fun insertionSortWhile(){
        var tmp= 0
        var j = 0
        for (i in 0 until data.size-1){
            tmp = data[i + 1]
            j = i+1
            while (j>=1 && tmp<data[j - 1]){
                data[j - 1] = data[j]
                j--
            }
            data[j] =tmp

        }
        show()
    }

    /**
     * 希尔排序 (分组插入)
     * 多排序的数组进行分段排序，越过插入排序的频繁判断
     * 让增量等于数组长度的一半
     * 根据增量把数组分为几部分，【0，0+gap】一组  【1，1+gap】一组 。。。
     * 增量等于增量的一半直到等于1
     * 一般的初次取序列的一半为增量，以后每次减半，直到增量为1。
     * 对这些组进行插入排序
     * */
    fun shellSort(){
        var gap = data.size/2
        while (gap<=1){

            for (i in gap until data.size-1){

                //进行0，0+gap一组  1，1+gap的插入排序
                var temp = data[i]
                var j = i-gap
                while (j>=0 && temp<data[j]){
                    data[j + gap] = data[j]
                    j -= gap
                }
                data[j + gap] = temp
            }
            gap /= 2
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
     * 交换后变成 7 1 2 【6】 9 ， 6为基准， 快速排的思想是左边全部小于基准，
     * 所以就有问题
     * */
    fun quicekSort(data: ArrayList<Int>, low: Int, height: Int):ArrayList<Int>{

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
        sort.addAll(quicekSort(data, low, i - 1))
        sort.add(target)
        sort.addAll(quicekSort(data, i + 1, height))
        return sort


    }

    /**
     * 归并排序
     * 把数组中间两边分别有序（递归过程，如果大于两个继续分割）
     * 然后将已经分割的开始合并起来，最后是两个有序数组的合并
     * */
    fun mergeSort(arr: IntArray) {
        sort(arr, 0, arr.size - 1)
    }

    fun sort(arr: IntArray, L: Int, R: Int) {
        if (L == R) {
            return
        }
        val mid = L + (R - L shr 1)
        sort(arr, L, mid)
        sort(arr, mid + 1, R)
        merge(arr, L, mid, R)
    }

    fun merge(arr: IntArray, L: Int, mid: Int, R: Int) {
        val temp = IntArray(R - L + 1)
        var i = 0
        var p1 = L
        var p2 = mid + 1
        // 比较左右两部分的元素，哪个小，把那个元素填入temp中
        while (p1 <= mid && p2 <= R) {
            temp[i++] = if (arr[p1] < arr[p2]) arr[p1++] else arr[p2++]
        }
        // 上面的循环退出后，把剩余的元素依次填入到temp中
        // 以下两个while只有一个会执行
        while (p1 <= mid) {
            temp[i++] = arr[p1++]
        }
        while (p2 <= R) {
            temp[i++] = arr[p2++]
        }
        // 把最终的排序的结果复制给原数组
        i = 0
        while (i < temp.size) {
            arr[L + i] = temp[i]
            i++
        }
    }


    private fun show(){
        Log.e("Algorithm", "排序结果：")
        data.forEach { i ->
            Log.e("Algorithm", i.toString())
        }
    }
    private fun show(arr: ArrayList<Int>){
        Log.e("Algorithm", "排序结果：")
        arr.forEach { i ->
            Log.e("Algorithm", i.toString())
        }
    }
}