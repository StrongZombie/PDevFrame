package hbb.example.test.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/**
 * @author HuangJiaHeng
 * @date 2020/5/22.
 * 服务接口
 */
public interface ITestService extends IInterface {

    /**
     * 接口方法
     * @return ParcelFileDescriptor
     * @throws RemoteException
     */
    public ParcelFileDescriptor getFileDescriptor()throws RemoteException;

    public void setValue(int val)throws RemoteException;

    /**
     *用于service通信的Binder对象，service端实现基类
     */
    public static abstract class Stub extends Binder implements ITestService{
        private static final String DESCRIPTOR = "com.hbb.test.testService";

        public Stub(){
            //创建时添加服务
            attachInterface(this,DESCRIPTOR);
        }

        //获取接口，用于client端调用
        public static ITestService asInterface(IBinder iBinder){
            if (iBinder == null){
                return null;
            }

            IInterface iin = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iin !=null && iin instanceof ITestService){
                return (ITestService)iin;
            }
            return new ITestService.Stub.Proxy(iBinder);
        }

        //获取binder对象，用于IPC
        @Override
        public IBinder asBinder(){
            return this;
        }

        //重新编组事务，实现跨进程通信，服务端和客户端进行交易
        //code 是一个整形的唯一标识，用于区分执行哪个方法，客户端会传递此参数，告诉服务端执行哪个方法;
        //data客户端传递过来的参数;
        //replay服务器返回回去的值;
        //flags标明是否有返回值，0为有（双向），1为没有（单向）。
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: { //接收方（Service）接收的描述
                    reply.writeString(DESCRIPTOR);//service端返回了文件描述（自定义）
                    return true;
                }
                case TRANSACTION_getFileDescriptor: { //当客户端调用这个方法
                    data.enforceInterface(DESCRIPTOR); //验证=是这个描述调用

                    ParcelFileDescriptor result = this.getFileDescriptor(); //获取服务端的文件描述符

                    reply.writeNoException(); //表示无异常

                    if (result != null) {
                        reply.writeInt(1); //服务端返回数字1，表示服务端已开启FileDescriptor
                        result.writeToParcel(reply, 0);//写入返回值，记录作用
                    } else {
                        reply.writeInt(0);
                    }

                    return true;
                }
                case TRANSACTION_setValue: { //客户端调用
                    data.enforceInterface(DESCRIPTOR);

                    int val = data.readInt();//客户端取值
                    setValue(val); //设置服务值

                    reply.writeNoException(); //表示无异常

                    return true;
                }
                default:
            }

            return super.onTransact(code, data, reply, flags);
        }

        /**
         * service的代理，Client端通过这个对象进行操作，规范方法
         * client获取Service的IBinder对象
         * */
        private static class Proxy implements ITestService{

            private IBinder mRemote;
            Proxy(IBinder mRemote){
                this.mRemote = mRemote;
            }

            //Client获取服务端的ParcelFileDescriptor
            @Override
            public ParcelFileDescriptor getFileDescriptor() throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                ParcelFileDescriptor result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR); //data.enforceInterface(DESCRIPTOR); 同样为判断

                    mRemote.transact(Stub.TRANSACTION_getFileDescriptor, data, reply, 0);//调用binder对象的getFileDescriptor方法

                    reply.readException();
                    if (0 != reply.readInt()) { //1表示服务端已开启FileDescription
                        result = ParcelFileDescriptor.CREATOR.createFromParcel(reply);
                    } else {
                        result = null;
                    }
                } finally {
                    reply.recycle();
                    data.recycle();
                }

                return result;
            }

            @Override
            public void setValue(int val) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(val);

                    mRemote.transact(Stub.TRANSACTION_setValue, data, reply, 0);

                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }

            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }


        }
        static final int TRANSACTION_getFileDescriptor = IBinder.FIRST_CALL_TRANSACTION + 0;
        static final int TRANSACTION_setValue = IBinder.FIRST_CALL_TRANSACTION + 1;

    }

}
