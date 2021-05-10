package com.springcloud.client.redis.demo;

import com.springcloud.client.EurekaClientApplication;
import com.springcloud.client.redis.constant.SystemConstants;
import com.springcloud.client.redis.service.PublishService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaClientApplication.class)
@WebAppConfiguration
public class RedisPublishTest {
    private final static Logger log = LoggerFactory.getLogger(RedisPublishTest.class);

    @Autowired
    private PublishService publishService;

    @Test
    public void testPublishService(){
        for(int i=0;i<10;i++) {
            publishService.publish(SystemConstants.TOPIC_NAME, "这是我发第"+i+"条的消息啊");
        }
    }

}