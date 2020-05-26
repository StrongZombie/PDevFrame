package hbb.example.test.delevopMode

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author HuangJiaHeng
 * @date 2020/2/26.
 */
class Behavior {

    /**
     * 策略模型
     * 把对象 对应复杂的操作抽象出来，（如算法等）
     * 把对象传入进行修饰
     * */
    class Strategy{
        //原先可以使用if-else来区别符号，进行操作
        class BeDo{

        }
        interface Do{
            fun justDo(be:BeDo)
        }
        class OneDo:Do{
            override fun justDo(be:BeDo) {
                Log.e("dd","第一种复杂操作")
            }
        }

        class TwoDo:Do{
            override fun justDo(be:BeDo) {
                Log.e("dd","第二种复杂操作")
            }
        }
        class ThreeDo:Do{
            override fun justDo(be:BeDo) {
                Log.e("dd","第三种复杂操作")
            }
        }
        fun main(){
            OneDo().justDo(BeDo())
            TwoDo().justDo(BeDo())
            ThreeDo().justDo(BeDo())
        }

    }

    /**
     * 观察者模式
     * 每隔一段时间跑去厨房找你妈，问她饭做好没（轮询）
     * 跟你妈说一声，”老妈，饭做好了马上叫我”（订阅或注册），你妈答应了你 
     * 的请求，等她做完饭后立马通知你，
     * */

    class Observer{
        /**
         * 普通实现
         * */
        interface People{
        }
        class Kid :People{

            fun eat(){
                Log.e("dd","傻孩子吃饭了")
            }

        }
        class Mama :People{

            var kidArray = ArrayList<Kid>()

            fun registerKid(kid:Kid){
                kidArray.add(kid)
            }

            fun unRegisterKid(kid:Kid){
                kidArray.remove(kid)
            }

            fun notifyKid(index:Int){
                kidArray[index].eat()
            }

        }

        /**
         * java.util.observer
         * 实现
         * */
        class KidObserver :java.util.Observer{
            override fun update(o: Observable?, arg: Any?) {
                Log.e("dd","收到mamao的呼喊，可以吃饭了，")
            }

        }
        class MamaObservable :Observable(){
            fun callKidToKid(){
                Log.e("dd","通知傻孩子吃饭")
                this.notifyObservers() //通知注册的观察者
            }
        }

        fun main(){
            var mm = Mama()
            var kid = Kid()
            mm.registerKid(kid)
            mm.notifyKid(0)

            var mmo = MamaObservable()
            var kido = KidObserver()
            mmo.addObserver(kido)
            mmo.callKidToKid()
        }

    }

    /**
     * 迭代器模式
     * 自己实现迭代器来管理数据
     * Iterator iterator = list.iterator();
     * while(iterator.hasNext()){
     * int i = (Integer) iterator.next();
     * System.out.println(i);

     * */

    class Iterator{
        interface Song{
            fun name():String
            fun title():String
        }
        class SingleSong(var name:String,var title:String):Song{
            override fun name():String {
                return name
            }

            override fun title():String {
                return title
            }

        }
        interface Iterator1{
            fun hasNext():Boolean
            fun next():SingleSong
        }

        class SongList(var list:Array<SingleSong>){

            var cursor:Int = 0

            private inner class SongIterator:Iterator1{

                override fun hasNext(): Boolean {
                    return list[cursor] != null
                }

                override fun next():SingleSong {
                    cursor++
                    return list[cursor]
                }

            }


            fun getIterator():Iterator1{
                return SongIterator()
            }
        }

        fun main(){
            var list =SongList(arrayOf(SingleSong("1","2")))
            var listIterator = list.getIterator()
            while (listIterator.hasNext()){
                var song = listIterator.next()
                Log.e("dd",song.name+"\t"+song.title)
            }
        }
    }

    /**
     * 命令模式
     * 把命令抽象出来，降低请求者和接受者的耦合
     * */
    class Command{
        interface Command1{
            fun execute()
        }
        //接收者
        class RequestDO(){
            fun toDo(){
                Log.e("dd","有人请求了，执行指令")
            }
        }
        //命令
        class PlayCommand(var rdo:RequestDO) :Command1{
            override fun execute() {
                rdo.toDo()
            }

        }
        //请求者
        class Invoke(){
            private lateinit var command:PlayCommand

            fun setCommandD(cmmand:PlayCommand){
                this.command = cmmand
            }
            fun play(){
                command.execute()
            }
        }

        fun main(){
            var invoke = Invoke().also { it.setCommandD(PlayCommand(RequestDO()))}
            invoke.play()
        }
    }

    /**
     * 备忘录模式
     * 用对象保存数据，用管理者来进行管理，需要回滚操作的时候运用
     * */
    class Memorandum{
         //保存的数据对象
        class Memo{
             var hp:Int
             var mp:Int
             var state:String
             constructor(hp:Int,mp:Int,state:String){
                 this.hp = hp
                 this.mp = mp
                 this.state = state
             }

         }
        //角色
        class Role{
            var hp:Int = 0
            var mp:Int = 0
            var state:String = "健康"

        }
        //管理者
        class RestoreMemo{
            var memoArray = ArrayList<Memo>()
            fun save(role:Role){
                memoArray.add(Memo(role.hp,role.mp,role.state))
            }
            fun getList():ArrayList<Memo>{
                return memoArray
            }
            fun restore(i:Int,role:Role){
                role.hp = memoArray[i].hp
                role.mp = memoArray[i].mp
                role.state = memoArray[i].state
            }
        }

    }

    /**
     * 中介模式
     * 使多对多变成一对多
     * 如很多房客对很多房客
     * 变为多房客对一个中介对多包猪婆
     * */

    class Intermediary{

        interface AInterface{
            fun name()
        }

        interface BInterface{
            fun name()
            fun addA(a:A)
            fun addB(b:B)
            fun callAll()
            fun callA()
            fun callB()
        }

        interface CInterface{
            fun name()
        }
        //对象A
        class A :AInterface{
            override fun name() {

            }
        }
        //中介B
        class B:BInterface{
            var aArray = ArrayList<A>()
            var bArray = ArrayList<B>()
            override fun name() {

            }

            override fun addA(a:A) {
                aArray.add(a)
            }

            override fun addB(b:B) {
                bArray.add(b)
            }

            override fun callAll() {
               aArray.forEach { _ ->

               }
                bArray.forEach { _ ->

                }
            }

            override fun callA() {
                aArray.forEach { _ ->

                }
            }

            override fun callB() {
                bArray.forEach { _ ->

                }
            }
        }

        //对象B
        class C:CInterface{
            override fun name() {

            }
        }
    }

    /**
     * 访问者模式
     * 保存对象结构不变，抽象新的可操作类型
     * 通过访问者来操作对象
     * */

    class Visitor{

        interface Man{
            fun play(machine:Machine)
        }
        interface Machine{
            fun getGame():String
        }
        class JumpMachine:Machine{
            override fun getGame(): String{
                return "跳舞机器"
            }

        }
        class RunMachine:Machine{
            override fun getGame(): String {
                return "跑步机器"
            }

        }
        class KillMachine:Machine{
            override fun getGame(): String {
                return "杀人机器"
            }

        }

        class MaleMan:Man{
            override fun play(machine: Machine) {
                Log.e("dd","男玩："+machine.getGame())
            }
        }

        class FmalMan:Man{
            override fun play(machine: Machine) {
                Log.e("dd","女玩："+machine.getGame())
            }

        }

        interface MachineRoomI{
            fun action(man:Man,machine: Machine)
        }
        //访问者
        class MachineRoom : MachineRoomI{
            override fun action(man:Man,machine: Machine) {
                man.play(machine)
            }


        }

    }


    /**
     * 责任链
     * */
    class ChainOfResponsibility{
        abstract class  Log( var level:Int = -1){
            companion object{
                val INFO = 0
                val DEBUG = 1
                val ERROR = 2
            }
            fun Log(level: Int,message:String){
                if (level>this.level){
                    if (nextLogger!=null){
                        nextLogger.Log(level,message)
                    }
                }else{
                    Log(message)
                }
            }
            lateinit var nextLogger:Log
            fun setnextLogger(logger:Log){
                nextLogger = logger
            }

            abstract fun Log(message: String)
        }

        data class consoleLog(var level1:Int):Log(level1){
            init {
                setnextLogger(debugLogger(Log.DEBUG))
            }
            override fun Log(message: String) {
                println("i ->$message")
            }
        }

        data class debugLogger(var level1:Int):Log(level1){
            init {
                setnextLogger(errorLogger(Log.ERROR))
            }
            override fun Log(message: String) {
                println("d ->$message")
            }
        }

        data class errorLogger(var level1:Int):Log(level1){

            override fun Log(message: String) {
                println("e ->$message")
            }
        }

        class AutoLoogger{
            var i = consoleLog(Log.INFO)
            var d = debugLogger(Log.DEBUG)
            var e = errorLogger(Log.ERROR)
            fun getLogger():Log{
                return i
            }
        }
        //使用
        fun main(){
            AutoLoogger().getLogger().Log(Log.ERROR,"123")
            AutoLoogger().getLogger().Log(Log.DEBUG,"123")
        }

    }
}