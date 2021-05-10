package com.springcloud.client.redis.service.impl;

import com.springcloud.client.redis.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service("publishService")
public class PublishServiceImpl implements PublishService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 订阅主题
     * @param topicName 发布的管道
     * @param message   发布的内容
     */
    @Override
    public void publish(String topicName,String message) {
        stringRedisTemplate.convertAndSend(topicName, message);
    }

}
