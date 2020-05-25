package com.springcloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ZhXiQi
 * @Title: 服务的注册和发现 —— eureka 客户端
 * @date 2019-07-22 11:14
 *
 * 注解 @EnableEurekaClient 表明自己是一个 eurekaClient
 * 使用feign，需要开启feign功能
 *
 * extends SpringBootServletInitializer
 *
 * @Override
 *     protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
 *         return builder.sources(EurekaClientApplication.class);
 *     }
 */
@EnableHystrix
@EnableFeignClients
@SpringCloudApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        System.setProperty("logging.level.org.springframework.boot.autoconfigure", "info");
        SpringApplication.run(EurekaClientApplication.class,args);
    }

}
