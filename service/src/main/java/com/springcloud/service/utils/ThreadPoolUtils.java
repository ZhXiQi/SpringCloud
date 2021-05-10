package com.springcloud.service.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-07-06 11:26
 */
public class ThreadPoolUtils {
    /**
     * 饿汉式单例
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors()+1,5000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
            .setNameFormat("thread-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    private ThreadPoolUtils(){}

    public static ExecutorService getExecutorService() {
        if (executorService == null){
            executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors()+1,5000,TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                    .setNameFormat("thread-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        }
        return executorService;
    }
}
