package hbb.example.test.structure




/**
 * @author HuangJiaHeng
 * @date 2020/3/19.
 */
class Tree<T>{

    var data:T
    var leftTree:Tree<T>?=null
    var rightTree:Tree<T>?=null

    constructor(data:T){
        this.data=data
        this.leftTree = null
        this.rightTree = null
    }

    constructor(data:T, leftTree:Tree<T>, rightTree:Tree<T>){
        this.data=data
        this.leftTree = leftTree
        this.rightTree = rightTree
    }

    fun addLeft(value:T){
        leftTree = Tree(value)
    }

    fun addRight(value:T){
        rightTree = Tree(value)
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj !is Tree<*>) {
            false
        } else {
            this.data!! ==(obj as Tree<T>).data
        }
    }
}