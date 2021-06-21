package hbb.example.test.coroutines

import hbb.example.test.coroutines.CoroutinesGuide.massiveRun
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/1/27
 *   desc  :
 * </pre>
 *
 */
sealed class CounterMsg
object IncCounter : CounterMsg() // 递增计数器的单向消息
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // 携带回复的请求

object ActorsGuilde {
    // 计数器 Actor 的各种类型

    // This function launches a new counter actor
    @ObsoleteCoroutinesApi
    fun CoroutineScope.counterActor() = actor<CounterMsg> {
        var counter = 0 // actor state
        //channel 是 调用的所有协程
        for (msg in channel) { // iterate over incoming messages
            when (msg) {
                //协程send过来的数据
                is IncCounter -> counter++
                is GetCounter -> msg.response.complete(counter) //回调
            }
        }
    }

    fun main() = runBlocking<Unit> {
        val counter = counterActor() // create the actor
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.send(IncCounter) //发送到actor通道
            }
        }
        // send a message to get a counter value from an actor
        val response = CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        println("Counter = ${response.await()}")
        counter.close() // shutdown the actor
    }
}