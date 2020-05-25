package com.springcloud.client.redis.service;

public interface DistributedLock {
    static final long TIMEOUT_MILLIS = 30000;

    static final int RETRY_TIMES = 10;

    static final long SLEEP_MILLIS = 500;

    boolean lock(String key);

    boolean lock(String key, int retryTimes);

    boolean lock(String key, int retryTimes, long sleepMillis);

    boolean lock(String key, long expire);

    boolean lock(String key, long expire, int retryTimes);

    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    /**
     * 根据key和随机value获取锁
     * @param key
     * @param value
     * @param expire
     * @param retryTimes
     * @param sleepMillis
     * @return
     */
    boolean lock(String key, String value, long expire, int retryTimes, long sleepMillis);

    /**
     * 可重入版本 加锁
     * @param key
     * @param value
     * @param expire
     * @param retryTimes
     * @param sleepMillis
     * @return
     */
    boolean reentrantLock(String key, String value, long expire, int retryTimes, long sleepMillis);

    boolean releaseLock(String key);

    /**
     * 根据key和value匹配释放锁
     * @param key
     * @param value
     * @return
     */
    boolean releaseLock(String key, String value);

    /**
     * 可重入版本 释放锁
     * @param key
     * @param value
     * @return
     */
    boolean releaseReentrantLock(String key, String value);
}
