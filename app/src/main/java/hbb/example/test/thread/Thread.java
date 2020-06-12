package hbb.example.test.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author HuangJiaHeng
 * @date 2020/6/4.
 */
public class Thread {
    public static void main(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "哈哈哈哈哈";
            }
        };
        Future<String> future = threadPoolExecutor.submit(callable);
        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPoolExecutor.shutdown();
    }
}
