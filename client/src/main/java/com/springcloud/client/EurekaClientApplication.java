package com.springcloud.client;

import org.springframework.aop.framework.AopContext;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
 *
 * @EnableAspectJAutoProxy 使用 AopContext 需要此注解支持
 * @EnableCaching 开启缓存支持
 */
@EnableCaching
@EnableHystrix
@EnableFeignClients
@SpringCloudApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class EurekaClientApplication {

    public static void main(String[] args) {
        System.setProperty("logging.level.org.springframework.boot.autoconfigure", "info");
        SpringApplication.run(EurekaClientApplication.class,args);
    }

    public void loadBean() {

        //获取当前对象的 spring proxy 对象，即bean
        EurekaClientApplication o = (EurekaClientApplication) AopContext.currentProxy();
        //通过xml(classpath下)配置文件加载bean
        ApplicationContext contextWithXml = new ClassPathXmlApplicationContext("XML configLocation");
        //通过xml文件加载bean
        ApplicationContext contextWithFile = new FileSystemXmlApplicationContext("xml file configLocation");
        /**
         * 通过注解方式加载bean，有四个构造器：
         * 1.无参
         * 2.包路径 ...basePackages
         * 3.类名 ...annotatedClasses
         * 4.bean工厂 beanFactory
         *
         */
        ApplicationContext contextWithAnnotation = new AnnotationConfigApplicationContext();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //对加入的 bean 进行更名
//        context.registerBean();

    }

}
