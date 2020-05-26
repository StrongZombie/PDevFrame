package hbb.example.test.structure.binarysearchTree


/**
 * @author HuangJiaHeng
 * @date 2020/4/25.
 * 二叉查找树
 * */

class BinarySearchTree<T>where T:Int{
    var data:T
    var leftTree:BinarySearchTree<T>?=null
    var rightTree:BinarySearchTree<T>?=null

    constructor(data:T,leftTree:BinarySearchTree<T>?,rightTree:BinarySearchTree<T>?){
        this.data =data
        this.leftTree =leftTree
        this.rightTree =rightTree
    }

    fun contains(data: T):Boolean{
        return contains(data,this)
    }

    private fun contains(data: T,node: BinarySearchTree<T>?):Boolean{
        if (node == null){
            return false
        }
        if (data == node.data){
            return true
        }
        return if (data >node.data){
            contains(data,node?.rightTree)
        }else{
            contains(data,node?.leftTree)
        }

    }

    fun insert(data:T):BinarySearchTree<T>?{
        return insert(data,this)
    }

    private fun insert(data:T,node:BinarySearchTree<T>?):BinarySearchTree<T>?{
        if (node == null){
            return BinarySearchTree(data,null,null)
        }

        if (data<node.data){
            node?.leftTree = insert(data,node?.leftTree)
        }else if (data>node.data){
            node.rightTree = insert(data,node?.rightTree)
        }
        return node
    }

    fun findMin():T?{
        return findMin(this)
    }

    private fun findMin(node:BinarySearchTree<T>?):T?{
        if (node==null){
            return null
        }
        return if (node.leftTree!=null){
            findMin(node.leftTree)
        }else{
            node.data
        }
    }

    fun findMax():T?{
        return findMax(this)
    }

    private fun findMax(node:BinarySearchTree<T>?):T?{
        if (node==null){
            return null
        }
        return if (node.rightTree!=null){
            findMin(node.rightTree)
        }else{
            node.data
        }
    }

    /**
     * 查找所有小于该值的最大值
     *
     */
    fun floor(data:T):BinarySearchTree<T>?{
        return floor(data,this)
    }

    private fun  floor(data:T,node:BinarySearchTree<T>?):BinarySearchTree<T>?{
        if (data == node?.data){
            return this.leftTree
        }

        if (node ==null){
            return null
        }

        return if (data>node.data){
            floor(data,node.rightTree)
        }else{
            return node.leftTree
        }
    }

    /**
     * 查找所有大于该值的最小值
     *
     */
    fun ceiling(data:T):BinarySearchTree<T>?{
        return floor(data,this)
    }

    private fun  ceiling(data:T,node:BinarySearchTree<T>?):BinarySearchTree<T>?{
        if (data == node?.data){
            return this.leftTree
        }

        if (node ==null){
            return null
        }

        return if (data<node.data){
            floor(data,node.leftTree)
        }else{
            return node.rightTree
        }
    }


    /**
     * 删除
     * */
    fun delete(data: T):BinarySearchTree<T>?{
        return delete(data,this)
    }

    private fun delete(data:T,node:BinarySearchTree<T>?):BinarySearchTree<T>?{
        var targetNode: BinarySearchTree<T> = node ?: return null

        when {
            data<targetNode.data -> {
                return delete(data,targetNode.leftTree)
            }
            data>targetNode.data -> {
                return delete(data,targetNode.rightTree)
            }
            else -> {
                if (targetNode.leftTree!=null && targetNode.rightTree!=null){
                    targetNode.data = findMin(targetNode.rightTree) as T
                    targetNode.rightTree = delete(targetNode.data,targetNode.rightTree)
                }else{
                    targetNode = if (targetNode.leftTree != null){
                        targetNode.leftTree!!
                    }else{
                        targetNode.rightTree!!
                    }
                }
            }
        }
        return targetNode

    }


}