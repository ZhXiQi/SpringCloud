package com.springcloud.client.redis.service.impl;

import com.springcloud.client.redis.service.CommonCacheService;
import com.springcloud.client.redis.service.DistributedLock;
import com.springcloud.client.redis.service.KeyValueCacheService;
import com.springcloud.client.redis.service.ListCacheService;
import com.springcloud.client.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean lock = distributedLock.lock("lock5", "124", 1000, 5, 10);
                String format = MessageFormat.format("thread1 get lock result:{0}", lock);
                log.info(format);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean lock = distributedLock.lock("lock5", "125", 1000, 5, 10);
                String format = MessageFormat.format("thread2 get lock result:{0}", lock);
                log.info(format);
            }
        });

        thread1.start();
        thread2.start();

        TimeUnit.SECONDS.sleep(1);

        /*List<List<List<List<String>>>> lists = new ArrayList<>();
        List<String> latestList = new ArrayList<>();
        latestList.add("1");
        latestList.add("2");

        List<List<String>> threeList = new ArrayList<>();
        threeList.add(latestList);
        threeList.add(new ArrayList<String>(){{add("3");}});

        List<List<List<String>>> twoList = new ArrayList<>();
        twoList.add(threeList);

        lists.add(twoList);

        keyValueCacheService.set("test",lists);*/
//        listCacheService.lpushAll("test", Collections.singletonList(lists));
//        listCacheService.lpush("test",lists);

    }

    @Test
    public void releaseLock() throws InterruptedException {
        System.out.println(distributedLock.releaseLock("lock5","123"));
        TimeUnit.SECONDS.sleep(1);
        System.out.println(distributedLock.releaseLock("lock5","124"));

        String test = keyValueCacheService.get("test");

        List<String> strings = Collections.singletonList(test);
        System.out.println(test.toString());

    }

    @Test
    public void test4HyperLogLog() throws InterruptedException {
        HyperLogLogOperations hyperLogLog = redisTemplate.opsForHyperLogLog();

        String hyperLogLogOne = "hyperLogLogOne";
        String hyperLogLogTwo = "hyperLogLogTwo";
        redisTemplate.opsForHyperLogLog().delete(hyperLogLogOne);
        redisTemplate.opsForHyperLogLog().delete(hyperLogLogTwo);

        new Thread(() -> {
            for (int i=0;i<1000;++i){
                Long add = hyperLogLog.add(hyperLogLogOne, "user2" + i);
                Long count = hyperLogLog.size(hyperLogLogOne);
//                System.out.println(count);
//                if (count != i+1){
//                    System.out.printf("%d %d\n",count,i+1);
//                    break;
//                }
            }
        }).start();

        new Thread(() -> {
            for (int i=0;i<1000;++i){
                hyperLogLog.add(hyperLogLogTwo,"user2"+i);
                Long count = hyperLogLog.size(hyperLogLogTwo);
//                System.out.println(count);
//                if (count != i+1){
//                    System.out.printf("%d %d\n",count,i+1);
//                    break;
//                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);
        Long hyperLogLogOneSize = hyperLogLog.size(hyperLogLogOne);
        Long hyperLogLogTwoSize = hyperLogLog.size(hyperLogLogTwo);

//        Long union = hyperLogLog.union(hyperLogLogOne, hyperLogLogTwo);

        System.out.printf("%s:%d\n",hyperLogLogOne,hyperLogLogOneSize);
        System.out.printf("%s:%d\n",hyperLogLogTwo,hyperLogLogTwoSize);
//        System.out.printf("%s:%d\n","union",union);

    }

    @Test
    public void test4Pipeline(){
        List list = redisTemplate.executePipelined((RedisCallback<List<Object>>) connection -> {
            //此时还是不会执行的
            Boolean aBoolean = connection.hSet("hell1".getBytes(), "hash1".getBytes(), "hellovalue1".getBytes());
            Boolean aBoolean1 = connection.hashCommands().hSet("hash".getBytes(), "hashField".getBytes(), "hashValue".getBytes());
            Long aLong = connection.hyperLogLogCommands().pfCount("hyperLogLog".getBytes());
            List<Object> result = new ArrayList<>();
            result.add(aBoolean);
            result.add(aLong);
            result.add(aBoolean1);

            System.out.println(result.toString());

            //返回后才会执行，因为返回后会覆盖值，所以此处不允许返回值
            return null;

        });

        System.out.println(list.toString());

    }

    @Test
    public void test4Keys(){
        Set keys = redisTemplate.keys("*");
        System.out.println(keys.toString());
        Long hyperLogLogOne = redisTemplate.opsForHyperLogLog().size("hyperLogLog");
        System.out.println(hyperLogLogOne);
    }

    @Test
    public void test4Trans(){
        //redisTemplate不保证在同一个连接中执行所有的操作，如果需要在同一个连接中执行操作，可以使用 SessionCallback
        redisTemplate.execute((RedisCallback<List<Object>>) connection ->{

            connection.multi();
            connection.setCommands().sAdd("key3".getBytes(),"value".getBytes());
            connection.zSetCommands().zAdd("keyZSet3".getBytes(),10,"valueZSet".getBytes());
            connection.exec();


            connection.multi();
            connection.setCommands().sAdd("key4".getBytes(),"value1".getBytes());
            connection.zSetCommands().zAdd("keyZSet4".getBytes(),11,"valueZSet".getBytes());
            connection.discard();
            return null;
        });

//        redisTemplate.multi();

    }

    @Test
    public void test4Scan(){

        long cursorId;
        Cursor<byte[]> execute = (Cursor<byte[]>) redisTemplate.execute((RedisCallback) connection -> {
            Cursor<byte[]> scan = connection.scan(ScanOptions.scanOptions().match("user*").count(1).build());

            System.out.println(scan.getCursorId());
            System.out.println(scan.getPosition());

            return scan;
        });
    }
        
    @Test
    public void test4Bit(){
        redisTemplate.execute((RedisCallback) connection -> {
            //零存 value:true代表1/false代表0
            connection.setBit("bit".getBytes(), 0l, true);
            //零取
            Boolean bit = connection.getBit("bit".getBytes(), 0l);
            System.out.println(bit);
            //整存 a 91
            connection.set("bitSet".getBytes(), "test".getBytes());
            //整取
            byte[] bytes = connection.get("bitSet".getBytes());
            System.out.println(Arrays.toString(bytes));


            connection.bitField("bitMaps".getBytes(), BitFieldSubCommands.create().set(BitFieldSubCommands.BitFieldType.UINT_8).valueAt(0l).to(1));
            connection.bitField("bitMaps".getBytes(), BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.UINT_8).valueAt(0l));
            connection.bitField("bitMaps".getBytes(), BitFieldSubCommands.create().incr(BitFieldSubCommands.BitFieldType.UINT_8).
                    overflow(BitFieldSubCommands.BitFieldIncrBy.Overflow.FAIL).valueAt(1).by(1));
            return null;
        });
    }

    /**
     * 简单限流
     * @param key
     * @param period 窗口大小
     * @param maxCount 允许的最大值
     */
    public void test4Limit(String key, int period, int maxCount){
        long now = System.currentTimeMillis();
        List list = redisTemplate.executePipelined((RedisCallback) connection -> {
            connection.multi();
            //记录信息，这里只是简单设个key，实际中可以加个 行为 标识，value和score都使用毫秒时间戳
            connection.zAdd(key.getBytes(), now, ("" + now).getBytes());
            //移除时间窗口之前的记录，剩下的都是时间窗口内的
            connection.zRemRangeByScore(key.getBytes(), 0, now - period * 1000);
            //获取窗口内的行为数量
            connection.zCard(key.getBytes());
            //为此zSet设置过期时间，避免冷用户持续占用内存
            connection.expire(key.getBytes(), period + 1);
            connection.exec();
            connection.close();
            return null;
        });

        //输出比较数量是否超标
        System.out.println((long) ((ArrayList) list.get(0)).get(2) <= maxCount);
    }

    @Test
    public void test4Limit(){
        for (int i=0;i<20;++i){
            test4Limit("limit",60,5);
        }
    }

    @Test
    public void test4Geo() {

        GeoOperations geoOperations = redisTemplate.opsForGeo();
        geoOperations.add("home",new Point(120.15,30.28),"hz");
        geoOperations.add("home",new Point(117.62,26.27),"sm");
        Distance distance = geoOperations.distance("home", "hz", "sm");
        System.out.println(distance.getValue());
        System.out.println(distance.getMetric());
        System.out.println(distance.getUnit());

        List position = geoOperations.position("home", "hz", "sm");
        System.out.println(position.toString());

        List hash = geoOperations.hash("home", "hz", "sm");
        System.out.println(hash.toString());

        GeoResults radius = geoOperations.radius("home", "hz", 10000000);
        System.out.println(radius.getContent().toString());

    }

    @Test
    public void test4Stream(){

        redisTemplate.execute((RedisCallback) connection -> {

            return null;
        });
    }

    @Test
    public void test4Mu(){

        String md5 = MD5Util.encrypt("libuyi156");
        System.out.println(md5);

        String format = MessageFormat.format("{0}:{1}", "12", 34);
        System.out.println(format);

        System.out.println(0.2 + 0.1);
        System.out.println(0.3 - 0.1);
        System.out.println(0.2 * 0.1);
        System.out.println(0.3 / 0.1);

        long a = 3;
        long b = 2;
        long c = a-b;
        System.out.println(c);

        double da = 0.3;
        double db = 0.2;
        double dc = da - db;
        System.out.println(dc);

        byte[] bitMaps = new byte[10];
        bitMaps[0] = 1;
        bitMaps[5] = 1;
        String s = Arrays.toString(bitMaps);
        String remove = StringUtils.remove(s.replace('[',' ').replace(']',' ').replace(',',' '), ' ');
        System.out.println(remove);
        System.out.println(s);
    }

    @Test
    public void test4set(){
        redisTemplate.opsForZSet().add("key","value",10);
        for (int i=0;i<1000;++i){
            redisTemplate.opsForValue().set("key"+i,"value"+i);
        }
    }

}