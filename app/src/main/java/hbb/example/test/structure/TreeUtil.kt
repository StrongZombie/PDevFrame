package hbb.example.test.structure

/**
 * @author HuangJiaHeng
 * @date 2020/3/19.
 */
object TreeUtil {

    /**
     * 获取根节点
     * */
    fun <T> getTreeNum(tree:Tree<T>?):Int{
        if (tree==null){
            return 0
        }
        return getTreeNum(tree?.leftTree)+ getTreeNum(tree?.rightTree)+1
    }
}