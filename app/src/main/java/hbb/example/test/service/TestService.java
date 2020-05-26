package hbb.example.test.service;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author HuangJiaHeng
 * @date 2020/5/22.
 * 自定义service
 */
public class TestService extends ITestService.Stub {
    /**
     * 通过MemoryFile打开共享文件夹
     * */
    private MemoryFile memoryFile;

    public TestService(){
        try {
            //打开4个字节的共享文件夹
            memoryFile = new MemoryFile("Test",4);
            setValue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 打开服务端的ParcelFileDescription
     */
    @Override
    public ParcelFileDescriptor getFileDescriptor(){
        ParcelFileDescriptor pfd = null;

        try {
            Method method = memoryFile.getClass().getDeclaredMethod("getFileDescriptor",MemoryFile.class);
            pfd = (ParcelFileDescriptor) method.invoke(memoryFile);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return pfd;
    }

    /**
     * 设置数字，客户端调用
     * */
    @Override
    public void setValue(int val) {
        if (memoryFile == null){
            return ;
        }

        byte[] buffer = new byte[4];
        buffer[0] = (byte)((val >>> 24) & 0xFF);
        buffer[1] = (byte)((val >>> 16) & 0xFF);
        buffer[2] = (byte)((val >>> 8) & 0xFF);
        buffer[3] = (byte)(val & 0xFF);
        try {
            memoryFile.writeBytes(buffer, 0, 0, 4);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
