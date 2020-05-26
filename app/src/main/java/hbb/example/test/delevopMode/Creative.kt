package hbb.example.test.delevopMode

/**
 * @author HuangJiaHeng
 * @date 2020/2/25.
 */

/**
 * 创造型设计模型
 * 工厂
 * 单例
 * 原型
 * 建造
 * 抽象工厂
 * */
class Creative {
    data class circle(var color:String)
    data class Rectangle(var color:String)
    /**
     * 工厂
     * */
    class Factory{
        class circleFactory(){
            fun getCircle(color: String):circle{
                return circle(color)
            }
        }
    }

    /**
     * 抽象工厂
     * 超级工厂模式
     * */
    class AbstractFactory{
        abstract class factory{

        }
        class CircleFactory:factory(){
            fun getCircle(Color:String):circle{
                return circle(Color)
            }
        }
        class RectangleFactory:factory(){
            fun getRectangle(Color:String):Rectangle{
                return Rectangle(Color)
            }
        }
        fun getFactory(type:String):factory{
            if (type == "circle"){
                return CircleFactory()
            }else{
                return RectangleFactory()
            }
        }
    }

    /**
     * 单例
     * */
    class Single{
        /**
         * lazy init true
         * Synchronized false
         * 懒汉式
         */
        class Single1{
            companion object{
                var util:Single1?=null
                    get() {
                        if (field==null){
                            field = Single1()
                        }
                        return field
                    }
            }
        }
        /**
         * lazy init true
         * Synchronized true
         * 懒汉式
         */
        class Single2{
            companion object{
                var util:Single?=null
                    @Synchronized
                    get() {
                        if (field==null){
                            field = Single()
                        }
                        return field
                    }
            }
        }
        /**
         * lazy init false
         * Synchronized true
         * 饿汉式
         */
        class Single3{
            companion object{
                var util = Single3()
            }
        }

        /**
         * lazy init true
         * Synchronized true
         * 双重枷锁
         */
        class Single4{
            companion object{
                var util = Single4()
                    get() {
                        if (field==null){
                            synchronized(Single4::class.java){
                                if (field==null){
                                    field = Single4()
                                }
                            }
                        }
                        return field
                    }
            }
        }
        /**
         * 静态内部类
         * Synchronized true
         * lazy init true
         * */
        class Single5{
            companion object{

                fun getInstance():Single5{
                    return Single5Holder.INSTANCE
                }
                private class Single5Holder{
                    companion object{
                        var INSTANCE = Single5()
                    }
                }
            }


        }


        /**
         * 枚举
         * 访问方式：SingletonEnum.INSTANCE.method();
        INSTANCE即为SingletonEnum类型的引用，得到它就可以调用
        枚举中的方法。既避免了线程安全问题，还能防止反序列化
        重新创建新的对象，但是失去了类的一些特性，没有延时加载，
        推荐使用。
         * */
         enum class SingletonEnum {
            INSTANCE;
            private var instance1:Single5 = Single5();

            fun getInstance():Single5 {
                return instance1;
            }
        }

        /**
         * 容器类实现单例
         * 更好的管理多张类别的单例
         * */
         class Single6 {
            companion object{
                val map:HashMap<String,Single6> = HashMap()
                fun get(key:String):Single6{
                    return if (map.contains(key)){
                        map[key]!!
                    }else{
                        var single6 = Single6()
                        map[key] = single6
                        single6
                    }
                }
            }
        }

    }

    /**
     * 建造
     * 将复杂对象的构建与它的表示分离开来，使得同样的构建过程可以
     * 创建不同的表示。举个简单例子：自定义游戏角色时，游戏角色由：
     * 性别，脸部，衣服三个部分组成，我们根据自己的喜好进行选择，
     * 最终胜出一个自定义的角色，不同的组成部分，最后生成的角色
     * 也不一样。
     * */
    class Builder{

        data class Circle(var color: String){
            var colors = ArrayList<String>()
            fun addColor(color: String){
                colors.add(color)

            }
        }

        class builder{
            fun addColor(color: String): builder {
                circle.addColor(color)
                return this
            }
            fun get():Circle{
                return circle
            }
        }
        companion object{
            private val circle = Circle("blue")

        }
        //使用
        fun main(){
           Builder.builder().addColor("red").get()
        }
    }

    /**
     * 原型模式
     * */
    class Shape :Cloneable{
        override fun clone(): Shape {
            var s = Shape()
            return s.clone()
        }
        fun main(){
            var s = Shape()
            var s1 = s.clone()
        }
    }

}