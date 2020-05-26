package hbb.example.test.aop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

import hbb.example.test.aop.anno.Behavior;
import hbb.example.test.aop.anno.CheckLoginK;
import hbb.example.test.aop.anno.RequirePermission;
import hbb.example.test.aop.anno.SingleClick;
import hbb.example.test.arcode.ARCodeVM;
import primary.student.wraplayout.WrapLayout;

/**
 * @author HuangJiaHeng
 * @date 2020/3/18.
 */
@Aspect
public class CheckLoginAspectJ {
    private static final String TAG = "CheckLogin";
    private Long lastClickTime = 0L;
    private boolean mInstalledArCode = true;
    private Session mSession;
    /**
     * 找到处理的切点
     * * *(..)  可以处理CheckLogin这个类所有的方法
     */
    @Pointcut("execution(@hbb.example.test.aop.anno.CheckLoginK  * *(..))")
    public void executionCheckLogin() {
    }


    @Pointcut("execution(@hbb.example.test.aop.anno.Behavior * *(..))")
    public void executionBehavior(){}

    @Pointcut("execution(@hbb.example.test.aop.anno.SingleClick * *(..))")
    public void executionSingleClick(){}

    @Pointcut("execution(@hbb.example.test.aop.anno.ArEnable * *(..))")
    public void executionArEnable(){}

    @Pointcut("execution(@hbb.example.test.aop.anno.RequirePermission * *(..))")
    public void checkPermission(){}

    @Pointcut("execution(@hbb.example.test.aop.anno.CheckArCodeInstall * *(..))")
    public void executionArCodeInstall(){}
    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("executionCheckLogin()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i(TAG, "checkLogin: ");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckLoginK checkLogin = signature.getMethod().getAnnotation(CheckLoginK.class);
        if (checkLogin != null) {
            Context context = (Context) joinPoint.getThis();
            if (false) {
                Log.i(TAG, "checkLogin: 登录成功 ");
                return joinPoint.proceed();
            } else {
                Log.i(TAG, "checkLogin: 请登录");
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }

    @Around("executionBehavior()")
    public Object behavior(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取调用方法类型
        MethodSignature ms = (MethodSignature)joinPoint.getSignature();
        //获取注解实参
        Behavior a =ms.getMethod().getAnnotation(Behavior.class);
        a.value();
        //调用方法
        return joinPoint.proceed();
    }

    @Around("executionSingleClick()")
    public Object singleClick(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature ms = (MethodSignature)joinPoint.getSignature();
        SingleClick sc =ms.getMethod().getAnnotation(SingleClick.class);
        Long interval= sc.value();
        if (interval == null){
            interval = 800L;
        }
        Log.e(TAG,"点击过快:"+lastClickTime);
        if (System.currentTimeMillis() - lastClickTime < interval){
            Log.e(TAG,"拒绝点击");
            return  null;
        }
        lastClickTime = System.currentTimeMillis();
        return joinPoint.proceed();
    }

    @Around("executionArEnable()")
    public Object ArEnable(ProceedingJoinPoint joinPoint) throws Throwable{
        ARCodeVM vm = (ARCodeVM)joinPoint.getThis();
        if (ARCodeVM.Companion.maybeEnableArButton(vm.getActivity())){
            Log.d("ArCode","支持");
            return joinPoint.proceed();
        }
        return null;
    }

    @Around("checkPermission()")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature ms = (MethodSignature)joinPoint.getSignature();
        RequirePermission rp= ms.getMethod().getAnnotation(RequirePermission.class);
        ARCodeVM vm = (ARCodeVM)joinPoint.getThis();
        ActivityCompat.requestPermissions(vm.getActivity(), rp.permission(),1001);
        return joinPoint.proceed();
    }

    @Around("executionArCodeInstall()")
    public Object checkArCodeInstall(ProceedingJoinPoint joinPoint) throws Throwable{
        ARCodeVM vm = (ARCodeVM)joinPoint.getThis();
        try {
            if (mSession == null) {
                switch (ArCoreApk.getInstance().requestInstall(vm.getActivity(), mInstalledArCode)) {
                    case INSTALLED:
                        // Success, create the AR session.
                        mSession = new Session(vm.getActivity());
                        vm.setMSession(mSession);
                        return joinPoint.proceed();
                    case INSTALL_REQUESTED:
                        // Ensures next invocation of requestInstall() will either return
                        // INSTALLED or throw an exception.
                        mInstalledArCode = false;
                        return null;
                }
            }
        } catch (UnavailableUserDeclinedInstallationException e) {
            // Display an appropriate message to the user and return gracefully.
            Toast.makeText(vm.getActivity(), "TODO: handle exception " + e, Toast.LENGTH_LONG)
                    .show();
        } catch (UnavailableDeviceNotCompatibleException e) {  // Current catch statements.
            Toast.makeText(vm.getActivity(), "设备不支持AR", Toast.LENGTH_LONG)
                    .show();

        }
        return null;
    }


}
