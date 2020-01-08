package com.springcloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author ZhXiQi
 * @Title: SpringCloudApplication 包含了 SpringBootApplication
 * @date 2019/8/27 11:29
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringCloudApplication
public class ServiceZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class,args);
    }
}
