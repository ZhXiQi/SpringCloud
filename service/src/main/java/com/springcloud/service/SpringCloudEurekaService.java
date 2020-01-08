package com.springcloud.service;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * @author ZhXiQi
 * @Title: 服务的注册和发现 —— eureka 服务端
 * @date 2018/5/23 下午4:08
 *
 * 这里我们使用 @SpringBootApplication指定这是一个 Spring Boot的应用程序
 * 启动一个服务注册中心，只需要一个注解 @EnableEurekaServer ，这个注解需要在springboot工程的启动application类上加
 * eureka是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳（因此可以在内存中完成），在默认情况下 eureka server也是一个eureka client，必须要指定一个server。eureka server的配置文件application.yml
 *
 * 注解 @EnableEurekaServer 表明自己是一个 eurekaServer
 *
 * @SpringCloudApplication 包含了 @SpringBootApplication
 *
 */
@EnableEurekaServer
@SpringCloudApplication
public class SpringCloudEurekaService {
    public static void main(String[] args) {
        /**
         * 在main方法中启动应用程序
         */
        SpringApplication.run(SpringCloudEurekaService.class,args);
    }

}
