package hbb.example.test.hook;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author HuangJiaHeng
 * @date 2020/3/18.
 */
public class HookManagerJ {

    /**
     * hook的核心代码
     * 这个方法的唯一目的：用自己的点击事件，替换掉 View原来的点击事件
     *
     * @param v hook的范围仅限于这个view
     */
    public static void hook(Context context, final View v) {//
        try {

            // 反射执行View类的getListenerInfo()方法，拿到v的mListenerInfo对象，这个对象就是点击事件的持有者
            Method m = View.class.getDeclaredMethod("getListenerInfo");
            m.setAccessible(true);
            Object listenerInfor = m.invoke(v);

            //要从这里面拿到当前的点击事件对象
            Class clz = Class.forName("android.view.View$ListenerInfo");
            final Field f = clz.getDeclaredField("mOnClickListener");
            final Object clickListener = f.get(listenerInfor);
            //2. 创建我们自己的点击事件代理类
            //   方式1：自己创建代理类
            //   ProxyOnClickListener proxyOnClickListener = new ProxyOnClickListener(onClickListenerInstance);
            //   方式2：由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
            // Proxy.newProxyInstance的3个参数依次分别是：
            // 本地的类加载器;
            // 代理类的对象所继承的接口（用Class数组表示，支持多个接口）
            // 代理类的实际逻辑，封装在new出来的InvocationHandler内
            Object proxyListener = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    Log.e("hook","点击");
                    return method.invoke(clickListener,objects);
                }
            });

            f.set(listenerInfor,proxyListener);

            //3. 用我们自己的点击事件代理类，设置到"持有者"中

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    // 还真是这样,自定义代理类
//    static class ProxyOnClickListener implements View.OnClickListener {
//        View.OnClickListener oriLis;
//
//        public ProxyOnClickListener(View.OnClickListener oriLis) {
//            this.oriLis = oriLis;
//        }
//
//        @Override
//        public void onClick(View v) {
//            Log.d("HookSetOnClickListener", "点击事件被hook到了");
//            if (oriLis != null) {
//                oriLis.onClick(v);
//            }
//        }
//    }
}