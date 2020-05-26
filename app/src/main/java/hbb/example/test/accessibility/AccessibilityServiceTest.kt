package hbb.example.test.accessibility

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AccessibilityServiceTest : AccessibilityService() {

    //中断服务
    override fun onInterrupt() {

    }

    //监听事件
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.WINDOWS_CHANGE_ACTIVE){
            var node = rootInActiveWindow
            getChildCount(node,0)
        }
//        rootInActiveWindow //当前屏幕界面
//        Log.e("Access","当前点击事件内容：" +
//                "packageName:${event?.packageName}\t" +
//                " type:${event?.eventType}\t" +
//                "className:${event?.className}\t" +
//                "window:${event?.windowId}\t" )
//        Log.e("Access","屏幕包含：" +
//                "window包含节点：${rootInActiveWindow.childCount}")



//        for ( i in rootInActiveWindow.actionList){
//            Log.e("Access","label：${i.label}\tid：${i.id}")
//        }
    }


    private fun getChildCount(node:AccessibilityNodeInfo,level:Int){
        if (node!=null){
            if (node.childCount>0){
                for (i in 0 until node.childCount){
                    if (node.getChild(i)!=null){
                        Log.e("Access","当前层级：${level} 组件：${node.getChild(i).className}\t " +
                                "文本信息：${node.getChild(i).text}\t" +
                                "文本id：${node.viewIdResourceName}")
                        getChildCount(node.getChild(i),level=level+1)
                    }
                }
            }
        }
    }
}
