package com.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author ZhXiQi
 * @Title: 服务的注册和发现 —— eureka 服务端
 * @date 2018/5/23 下午4:08
 *
 * 这里我们使用 @SpringBootApplication指定这是一个 Spring Boot的应用程序
 * 启动一个服务注册中心，只需要一个注解 @EnableEurekaServer ，这个注解需要在springboot工程的启动application类上加
 * eureka是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳（因此可以在内存中完成），在默认情况下 eureka server也是一个eureka client，必须要指定一个server。eureka server的配置文件application.yml
 *
 * 注解 @EnableEurekaServer 表明自己是一个 eurekaServer,使用此注解才会有eureka的管理界面
 *
 * 注解@SpringCloudApplication包括：
 *  @SpringBootApplication、
 *  @EnableDiscoveryClient、
 *  @EnableCircuitBreaker，
 * 分别是SpringBoot注解、注册服务中心Eureka注解、断路器注解。对于SpringCloud来说，这是每一微服务必须应有的三个注解，所以才推出了@SpringCloudApplication这一注解集合
 * 使用@SpringCloudApplication需要进入Springboot依赖外，还需要引入[eureka-client] 和 [hystrix] 两个依赖：
 *
 */
@ServletComponentScan
@EnableEurekaServer
@SpringCloudApplication
public class SpringCloudEurekaService {

    public static void main(String[] args) {
        //设置属性
//        System.setProperty("spring.devtools.restart.enabled", "false");
//        System.setProperty("spring.devtools.add-properties", "false");
//        System.setProperty("spring.devtools.restart.log-condition-evaluation-delta", "false");
//        System.setProperty("logging.level.org.springframework.boot.autoconfigure", "info");

        /**
         * 在main方法中启动应用程序
         */
        SpringApplication.run(SpringCloudEurekaService.class,args);
    }

    @Component
    class B {
        private A a;

//        @Autowired
//        public B(A a) {
//            this.a = a;
//        }

        @Autowired
        public void setA(A a) {
            this.a = a;
        }
    }

    @Component
    class A {
        private B b;
//        @Autowired
//        public A(B b) {
//            this.b = b;
//        }

        @Autowired
        public void setB(B b) {
            this.b = b;
        }
    }

    class Base {

    }

    class LevelOne extends Base {

    }



}
