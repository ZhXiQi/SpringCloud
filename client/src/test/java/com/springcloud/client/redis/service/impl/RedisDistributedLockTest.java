package com.springcloud.client.redis.service.impl;

import com.springcloud.client.redis.service.CommonCacheService;
import com.springcloud.client.redis.service.DistributedLock;
import com.springcloud.client.redis.service.KeyValueCacheService;
import com.springcloud.client.redis.service.ListCacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/5/17 23:44
 * 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它会去寻找 主配置启动类（被 @SpringBootApplication 注解的）
 * 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RedisDistributedLockTest {

    @Resource
    private DistributedLock distributedLock;
    @Resource
    private KeyValueCacheService keyValueCacheService;
    @Resource
    private ListCacheService listCacheService;
    @Resource
    private RedisTemplate redisTemplate;

    @Before
    public void setBefore(){
//        log.info("error");
    }


    @Test
    public void lock() throws InterruptedException {
        System.out.println(distributedLock.lock("codehole","123",1000,5,1000));
        TimeUnit.SECONDS.sleep(1);
        System.out.println(distributedLock.lock("codehole2","124",10,5,1000));

        List<List<List<List<String>>>> lists = new ArrayList<>();
        List<String> latestList = new ArrayList<>();
        latestList.add("1");
        latestList.add("2");

        List<List<String>> threeList = new ArrayList<>();
        threeList.add(latestList);
        threeList.add(new ArrayList<String>(){{add("3");}});

        List<List<List<String>>> twoList = new ArrayList<>();
        twoList.add(threeList);

        lists.add(twoList);

        keyValueCacheService.set("test",lists);
//        listCacheService.lpushAll("test", Collections.singletonList(lists));
//        listCacheService.lpush("test",lists);

    }

    @Test
    public void releaseLock() throws InterruptedException {
        System.out.println(distributedLock.releaseLock("codehole","123"));
        TimeUnit.SECONDS.sleep(1);
        System.out.println(distributedLock.releaseLock("codehole2","124"));

        String test = keyValueCacheService.get("test");

        List<String> strings = Collections.singletonList(test);
        System.out.println(test.toString());

    }

    @Test
    public void test4HyperLogLog(){
        HyperLogLogOperations hyperLogLog = redisTemplate.opsForHyperLogLog();

        String hyperLogLogOne = "hyperLogLogOne";
        String hyperLogLogTwo = "hyperLogLogTwo";
        new Thread(() -> {
            for (int i=0;i<1000;++i){
                hyperLogLog.add(hyperLogLogOne,"user"+i);
                Long count = hyperLogLog.size(hyperLogLogOne);
                if (count != i+1){
                    System.out.printf("%d %d\n",count,i+1);
                    break;
                }
            }
        }).start();

        new Thread(() -> {
            for (int i=0;i<1000;++i){
                hyperLogLog.add(hyperLogLogTwo,"user"+i);
                Long count = hyperLogLog.size(hyperLogLogTwo);
                if (count != i+1){
                    System.out.printf("%d %d\n",count,i+1);
                    break;
                }
            }
        }).start();

        Long hyperLogLogOneSize = hyperLogLog.size(hyperLogLogOne);
        Long hyperLogLogTwoSize = hyperLogLog.size(hyperLogLogTwo);

        Long union = hyperLogLog.union(hyperLogLogOne, hyperLogLogTwo);

        System.out.printf("%s:%d\n",hyperLogLogOne,hyperLogLogOneSize);
        System.out.printf("%s:%d\n",hyperLogLogTwo,hyperLogLogTwoSize);
        System.out.printf("%s:%d\n","union",union);

    }

    @Test
    public void test4Pipeline(){
        redisTemplate.multi();
        redisTemplate.exec();
        redisTemplate.discard();
//        redisTemplate.executePipelined()
    }

    @Test
    public void test4Keys(){
        Set keys = redisTemplate.keys("*");
        System.out.println(keys.toString());
    }

}