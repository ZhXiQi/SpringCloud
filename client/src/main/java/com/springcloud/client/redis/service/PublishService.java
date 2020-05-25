package com.springcloud.client.redis.service;

public interface PublishService {
    public void publish(String topicName,String message);
}
