package com.springboot.core.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author ZhXiQi
 * @Title: @ConfigurationProperties(prefix = "aliyun.oss")注解只是表面此类中的属性读取配置文件中以prefix前置开头的对应数据，并不会将此类作为bean置于spring容器中，需要加
 * @Component注解表面此类作为一个主键，注入spring容器
 * 或者在启动类上加 @EnableConfigurationProperties(value = {xxx.class}) 这个注解指明
 * 需要
 *      <groupId>org.springframework.boot</groupId>
 *      <artifactId>spring-boot-configuration-processor</artifactId>
 * 支持
 * @date 2020/1/2 21:36
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String cdnSite;

    /**
     * 加载次一级对象，如aliyun.oss:
     *                      test:
     *                          a: xxx
     *                          b: xxx
     *                          c: xxx
     *        test对象里面有a、b、c三个属性
     */
    @NestedConfigurationProperty
    private Object test;
}
