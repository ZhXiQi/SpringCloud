package com.springcloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ZhXiQi
 * @Title: 服务的注册和发现 —— eureka 客户端
 * @date 2019-07-22 11:14
 *
 * 注解 @EnableEurekaClient 表明自己是一个 eurekaClient
 */
//@SpringBootApplication
//@EnableEurekaClient
@SpringCloudApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        System.setProperty("logging.level.org.springframework.boot.autoconfigure", "info");
        SpringApplication.run(EurekaClientApplication.class,args);
    }

}
