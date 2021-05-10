package com.springcloud.client.redis.service.impl;

import com.springcloud.client.redis.service.AbstractDistributedLock;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("redisDistributedLock")
public class RedisDistributedLock extends AbstractDistributedLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ThreadLocal<String> lockFlag = new ThreadLocal<String>();

    private ThreadLocal<Map<Thread,Integer>> exclusiveOwnerThread = new ThreadLocal<>();

    /**
     * Redis锁解锁 lua 脚本内容
     */
    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }


    public RedisDistributedLock() {
        super();
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = setNx(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while((!result) && retryTimes-- > 0){
            try {
                log.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setNx(key , expire);
        }
        return result;
    }

    @Override
    public boolean lock(String key, String value, long expire, int retryTimes, long sleepMillis) {
        boolean result = setNx(key, value, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while((!result) && retryTimes-- > 0){
            try {
                log.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setNx(key, value, expire);
        }
        return result;
    }

    @Override
    public boolean reentrantLock(String key, String value, long expire, int retryTimes, long sleepMillis) {
        Map<Thread, Integer> threadIntegerMap = currentLockers();
        Thread currentThread = Thread.currentThread();
        Integer lockNum = threadIntegerMap.get(currentThread);
        if (lockNum != null){
//            Long preExpire = redisTemplate.getExpire(key);
            Boolean expired = redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            if (expired!=null && expired){
                threadIntegerMap.put(currentThread, lockNum+1);
                return true;
            }
            return false;
        }
        boolean ok = this.lock(key, value, expire, retryTimes, sleepMillis);
        if (ok){
            threadIntegerMap.put(currentThread,1);
            return true;
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                String value = lockFlag.get();
                return connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN ,1,
                        key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8")));
            };
            return (Boolean)redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("release lock occured an exception", e);
        } finally {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            lockFlag.remove();
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key, String value) {
        try {
            RedisCallback<Boolean> callback = (connection) -> {
                return connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN ,1,
                        key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8")));
            };
            return (Boolean)redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("release lock occured an exception", e);
        }
        return false;
    }


    @Override
    public boolean releaseReentrantLock(String key, String value) {
        Map<Thread, Integer> threadIntegerMap = exclusiveOwnerThread.get();
        Thread currentThread = Thread.currentThread();
        Integer lockNum = threadIntegerMap.get(currentThread);
        if (lockNum == null){
            return false;
        }
        lockNum -= 1;
        if (lockNum > 0){
            threadIntegerMap.put(currentThread,lockNum);
        }else {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            threadIntegerMap.remove(currentThread);
            this.releaseLock(key,value);
        }
        return true;
    }

    private boolean setNx(final String key, final long expire ) {
        try{
            RedisCallback<Boolean> callback = (connection) -> {
                String uuid = UUID.randomUUID().toString();
                lockFlag.set(uuid);
                return connection.set(key.getBytes(Charset.forName("UTF-8")), uuid.getBytes(Charset.forName("UTF-8")),
                        Expiration.milliseconds(expire), RedisStringCommands.SetOption.SET_IF_ABSENT);
            };
            return (Boolean)redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("redis lock error.", e);
        }
        return false;
    }

    /**
     * lettuce版本
     * @param key key值
     * @param value value值
     * @param expiredTime 过期时间（秒）
     * @return
     */
    private boolean setNx(final String key, final String value, final long expiredTime) {
        Boolean resultBoolean = null;
        try {
            resultBoolean = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                String redisResult = "";
                @SuppressWarnings("unchecked")
                RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
                //lettuce连接包下序列化键值，否则无法用默认的ByteArrayCodec解析
                byte[] keyByte = stringRedisSerializer.serialize(key);
                byte[] valueByte = stringRedisSerializer.serialize(value);
                // lettuce连接包下 redis 单机模式setnx
                if (nativeConnection instanceof RedisAsyncCommands) {
                    RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
                    //同步方法执行、setnx禁止异步
                    redisResult = commands
                            .getStatefulConnection()
                            .sync()
                            .set(keyByte, valueByte, SetArgs.Builder.nx().ex(expiredTime));
                }
                // lettuce连接包下 redis 集群模式setnx
                if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                    RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                    redisResult = clusterAsyncCommands
                            .getStatefulConnection()
                            .sync()
                            .set(keyByte, valueByte, SetArgs.Builder.nx().ex(expiredTime));
                }
                //返回加锁结果
                return redisResult!=null && "OK".equalsIgnoreCase(redisResult);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBoolean != null && resultBoolean;
    }

    /**
     * 获取当前线程持有的重入次数
     * @return
     */
    private Map<Thread,Integer> currentLockers(){
        Map<Thread, Integer> threadIntegerMap = exclusiveOwnerThread.get();
        if (threadIntegerMap!=null) return threadIntegerMap;
        exclusiveOwnerThread.set(new HashMap<>());
        return exclusiveOwnerThread.get();
    }

}


