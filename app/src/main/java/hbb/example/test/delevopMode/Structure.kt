package hbb.example.test.delevopMode

import android.util.Log

/**
 * @author HuangJiaHeng
 * @date 2020/2/25.
 */
class Structure{
    /***
     * 适配器模式
     *  adapter
     *  接口适配类
     *  增加中间类来连接两个类的互动
     */
    class Adapter{
        interface player{
            fun play(path:String)
        }

        open class File{
            fun getPath():String{
                return "路径"
            }
        }

        class Mp4Player:player{
            override fun play(path: String) {
                Log.e("dd","播放Mp4$path 的视频")
            }
        }

        class AviPlayer:player{
            override fun play(path: String) {
                Log.e("dd","播放Avi$path 的视频")
            }

        }
        //类适配
        class Mp4PlayerAdapterClass : File(), player{
            var mp4 = Mp4Player()
            var avi = AviPlayer()
            override fun play(path: String) {
               if (getPath().contains("avi")){
                   avi.play(getPath())
               } else{
                   mp4.play(getPath())
               }
            }
        }

        //对象适配
        class Mp4PlayerAdapter():player{

            var mp4 = Mp4Player()
            var avi = AviPlayer()
            override fun play(path: String) {
                if (path.contains("avi")){
                    avi.play(path)
                }else{
                    mp4.play(path)
                }
            }

        }

        fun main(){
            Mp4PlayerAdapter().play(File().getPath())
        }

    }

    /**
     * 桥接
     * 把变化因素抽象出来，降低耦合度
     * */
    class Bridge{
        interface File{
            fun getName():String
        }
        class RedFile:File{
            override fun getName(): String {
                return "小红书"
            }
        }

        class YellowFile:File{
            override fun getName(): String {
                return "小黄书"
            }
        }

        interface BaoZhuang{
            fun setBao()
        }

        class RedBao(var f:File):BaoZhuang{
            override fun setBao() {
                Log.e("dd","红色包装的"+f.getName())
            }
        }

        class YellowBao(var f:File):BaoZhuang{
            override fun setBao() {
                Log.e("dd","黄色包装的"+f.getName())
            }
        }

    }




    /**
     * 过滤
     * */
    class data{
        var list = ArrayList<String>()
        fun get1():String{
            for (i in list){
                if (i == "1"){
                    return i
                }
            }
            return ""
        }
    }

    /**
     * 组合
     * 用一个大对象来维护相同子类的对象
     * */
    interface Product{
        fun name()
    }

    class LeftProduct:Product{
        override fun name() {
            Log.e("dd","左子部件")
        }
    }

    class RightProduct:Product{
        override fun name() {
            Log.e("dd","右子部件")
        }
    }

    class CenterProduct:Product{
        override fun name() {
            Log.e("dd","中间部件")
        }
    }

    class MainProduct:Product{
        var list = ArrayList<Product>()
        override fun name() {
            list.forEach {
                it.name()
            }
        }

    }


    /**
     * 装饰器
     * 传入对象进行装饰
     *
     * */
    class Decorator{

        interface Product{
            fun name()
        }

        class LeftProduct:Product{
            override fun name() {
                Log.e("dd","左子部件")
            }
        }

        interface Decorator1{
            fun setName()
        }

        class CircleDecorator(var d:Product) :Decorator1{
            override fun setName() {
                Log.e("dd","超级"+d.name())
            }

        }

    }

    /**
     * 外观
     * 实例化
     * 把负责对象操作进行封装成一个对象暴露调用
     * 多个对象关联暴露成一个对象
     * */

    class One{
        fun doit(){
            Log.e("dd","使用一技能")
        }
    }

    class Two{
        fun doit(){
            Log.e("dd","使用二技能")
        }
    }

    class Three{
        fun doit(){
            Log.e("dd","使用三技能")
        }
    }

    class YaSe{
        var one = One()
        var Two = Two()
        var Three = Three()
        fun killhe(){
            Two.doit()
            one.doit()
            Three.doit()
        }
    }

    /**
     * 享元
     * 用表维护对象，减少对象的创建
     * */
    class shape(){
        companion object{
            val map = HashMap<String,Creative.circle>()
            fun getShape(color:String):Creative.circle{
                var c = map.get(color)
                if (c==null){
                    c = Creative.circle(color)
                    map.put(color,c)
                }
                return c
            }
        }
    }

    /**
     * 代理
     * 他的方法
     * */
    class Proxy{

        interface Shape{
            fun color()
        }

        class Circle:Shape{
            override fun color() {
                Log.e("dd","红色的")
            }

        }

        class ProxyCircle(var c:Circle):Shape{
            override fun color() {
                Log.e("dd","圆的颜色是：")
                c.color()
            }
        }
    }


}