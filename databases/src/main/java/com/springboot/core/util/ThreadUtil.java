package com.springboot.core.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
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
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum+1,0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    private ThreadUtil(){}

    public static ExecutorService getExecutorService() {
        if (executorService == null){
            executorService = new ThreadPoolExecutor(threadNum, threadNum,0L,TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        }
        return executorService;
    }
}
