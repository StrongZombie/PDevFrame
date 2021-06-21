package hbb.example.test.eventmanager.bean

import hbb.example.test.eventmanager.Subscribe
import java.lang.reflect.Method

/**
 * <pre>
 *   author: hjh
 *   time  : 2021/1/20
 *   desc  :
 * </pre>
 *
 */
data class SubscribeBean(
    val method: Method,
    val annotation: Subscribe,
    val callInfo: Any
)