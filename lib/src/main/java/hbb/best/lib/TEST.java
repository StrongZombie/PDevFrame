package hbb.best.lib;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author HuangJiaHeng
 * @date 2020/6/4.
 */
public class TEST {
    public static void main(String [] args) {
//        ArrayBlockingQueue：由数组结构组成的有界阻塞队列
//        LinkedBlockingQueue：由链表结构组成的有界阻塞队列
//        PriorityBlockingQueue：支持优先级排序的无界阻塞队列
//        DelayQueue：使用优先级队列实现的无界阻塞队列
//        SynchronousQueue：不存储元素的阻塞队列
//        LinkedTransferQueue：由链表结构组成的无界阻塞队列
//        LinkedBlockingDeque：由链表结构组成的双向阻塞队列
        try {
//            synchronousQueueDemo();
//            reentrantLockDemo();
            cesDemo();
        }catch (Exception e){
            e.printStackTrace();
        }
//        MainViewModelKt
    }


    /**
     * ces操作，校验原子性操作
     * */
    static int result = 0; //普通会造成脏数据
    static AtomicInteger atoResult = new AtomicInteger(0);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(0, 1);  //解决ABA问题的AtomicStampedReference ,通过版本号
    private static void cesDemo(){
        for (int i =0; i < 5; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 100; i++){
                            Thread.sleep(10);
                            atomicStampedReference.compareAndSet(atomicStampedReference.getReference(),atomicStampedReference.getReference()+1,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
//                            System.out.println(  atoResult.incrementAndGet());
                            System.out.println(atomicStampedReference.getReference());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    /**
     * 重入锁
     * */
    private static void reentrantLockDemo() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for(int i=1;i<=3;i++){
            try {
                Thread.sleep(1000);
                System.out.println("顶顶顶顶顶");
            } finally {
                lock.unlock(); //可重入锁，需要解锁三次才能运行结束
            }
        }
    }

    /**
     * 不存储元素每次put take 都阻塞，一一对应
     * */
    private static void synchronousQueueDemo() throws InterruptedException {
        final SynchronousQueue<String> queue = new SynchronousQueue<String>();
        Thread putThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("synchronousQueueDemo=====>put thread start");
                try {
                    queue.put("1");   //阻塞，等到take后再操作
                } catch (InterruptedException e) {
                }
                System.out.println("put thread end");
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("take thread start");
                try {
                    System.out.println("take from putThread: " + queue.take());
                } catch (InterruptedException e) {
                }
                System.out.println("take thread end");
            }
        });

        putThread.start();
        Thread.sleep(1000);
        takeThread.start();
    }
}
