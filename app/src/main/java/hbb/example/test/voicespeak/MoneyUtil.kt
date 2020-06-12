package hbb.example.test.voicespeak


/**
 * @author HuangJiaHeng
 * @date 2020/6/4.
 * 金钱转中文金钱串
 */
class MoneyUtil {
    companion object{

        private val CHIN_NUM = arrayListOf('元', '拾', '佰', '仟')

        /**
         * 金钱转中文金钱串
         * 亿以上只会添加亿   如  1亿零拾亿1仟万元
         * （~ ~）见识短不知道亿后是啥单位，知道的可以在递归深度处添加）
         * 支持小数，支持多位数
         * @param money 字符串类型的金额
         * */
        fun readString(money: String):String{
            return if (money.contains('.')){
                getMoneyWithDot(money)
            }else{
                getMoneyWithoutDot(money,true)
            }
        }

        /**
         * 获取中文金钱（含小数点）
         * @param money
         * */
        private fun getMoneyWithDot(money: String):String{
            var sb = StringBuffer()
            var moneyChar = money.toCharArray()
            var dotIndex = moneyChar.indexOf('.')
            //整数部分
            sb.append(getMoneyWithoutDot(money.substring(0,dotIndex),false))
            //小数点
            sb.append("点")
            //小数部分
            for (i in dotIndex+1 until moneyChar.size){
                sb.append(moneyChar[i])
            }

            sb.append("元")

            return sb.toString();
        }

        /**
         * 获取整数部分的金额
         * @param money 金额
         * @param appendUnit 是否带单位（元）
         * */
        private fun getMoneyWithoutDot(money:String,appendUnit:Boolean,depth:Int=0):String{

            var sb = StringBuffer()

            //是否迭代递归运算
            var isCalc = true

            //特殊情况
            if (money == "0"){
                sb.append("0元")
                isCalc = false
            }else if (money=="10"){
                sb.append("拾元")
                isCalc = false
            } else if (money.length==2 && money[0] =='1'){
                sb.append("拾${money[1]}元")
                isCalc = false
            }

            if (isCalc){
                var moneyChar = money.toCharArray()

                //万元起,每四位数进行一次递归 如1 0000
                if (money.length-4>0){
                    var depth = depth+1 //递归深度
                    //先解析前面的1 返回的是1
                    sb.append(getMoneyWithoutDot(money.substring(0,money.length-4),false,depth))
                    //判断解析的位数是否全为0 如 1（亿） 0000（不添加） 0000 应该度1亿元
                    if (money.substring(if (money.length-8>0)money.length-8 else 0,money.length-4).toFloat()>0){
                        //两段深度则加亿，一段深度则加万，三段以上加亿   如 1 （亿） 0010 （万） 0000 （元）
                        when(depth){
                            1 -> sb.append("万")
                            2 -> sb.append("亿")
                            else -> sb.append("亿")
                        }
                    }
                    //递归添加最后四位 如1 0000 则解析 0000 无返回
                    sb.append(getMoneyWithoutDot(money.substring(money.length-4,money.length),appendUnit))
                }else{
                    //遍历中文串
                    for (i in moneyChar.indices){
                        //是0的情况 查看后一位，如果有值则加0 如果还是0则不添加
                        if (moneyChar[i]=='0' ){
                            if (i+1 < moneyChar.size){
                                if (moneyChar[i+1] !='0'){
                                    sb.append("0")
                                }
                            }
                        }else{
                            //不是0的情况下，添加 数字+ '拾'或'佰'或'仟'
                            sb.append("${moneyChar[i]}${CHIN_NUM[moneyChar.size-i-1]}")
                        }
                    }
                }
            }
            //是否需要带单位，如果是不带小数点最后四位的解析则需要 带小数点则不需要
            if (!appendUnit && sb.contains("元")){
                sb.deleteCharAt(sb.indexOf("元"))
            }else if(appendUnit && !sb.contains("元")){
                    sb.append("元")
            }
            return sb.toString()
        }

    }

}