package com.springcloud.client.utils;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhXiQi
 * @Title: 多线程单例,使用时需要自己设定线程名称
 * @date 2019-03-23 16:21
 */
public class ThreadUtil {

    private static int threadNum = Runtime.getRuntime().availableProcessors();
    /**
     * 饿汉式单例,自己设置线程池的各个参数
     * 线程在start后才会执行run方法，run方法的执行表示这个task真正被线程运行了，这时线程的名称也就确定了。所以可以在run的第一句加上 Thread.currentThread().setName("Name");
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum + 1, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"threadPoolName");
        }
    }, new ThreadPoolExecutor.AbortPolicy());

    private ThreadUtil(){}

    public static ExecutorService getExecutorService() {
        if (executorService == null){
            executorService = new ThreadPoolExecutor(threadNum, threadNum,0L,TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        }
        return executorService;
    }

    /**
     * Google guava 里的 ThreadFactory，可以设置 线程名称格式(包含线程ID)，和异常处理方式
     * @return
     */
    public static ExecutorService getExecutorServiceWithThreadFactoryBuilder() {
        if (executorService == null){
            executorService = new ThreadPoolExecutor(threadNum, threadNum,0L,TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                    .setNameFormat("ThreadUtil-pool-%d")
                    .setUncaughtExceptionHandler((thread, e) -> {
                        //handler exception
                    })
                    .build(), new ThreadPoolExecutor.AbortPolicy());
        }
        return executorService;
    }

    /**
     * commons-lang3 包里的 ThreadFactory，可以设置 线程名称格式(包含线程ID)，和异常处理方式
     * @return
     */
    public static ExecutorService getExecutorServiceWithBasicThreadFactoryBuilder() {
        if (executorService == null){
            executorService = new ThreadPoolExecutor(threadNum, threadNum,0L,TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), new BasicThreadFactory.Builder()
                    .namingPattern("ThreadUtil-pool-%d")
                    .uncaughtExceptionHandler((thread, e) -> {
                        //handler exception
                    })
                    .build(), new ThreadPoolExecutor.AbortPolicy());
        }
        return executorService;
    }
}
