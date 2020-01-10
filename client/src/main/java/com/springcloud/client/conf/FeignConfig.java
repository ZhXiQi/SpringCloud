package com.springcloud.client.conf;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZhXiQi
 * @Title: feign上传文件设置
 * @date 2019/8/27 20:49
 */
public class FeignConfig {

    static Encoder encoder = new SpringFormEncoder(new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
        @Override
        public HttpMessageConverters getObject() throws BeansException {
            return new HttpMessageConverters(new RestTemplate().getMessageConverters());
        }
    }));

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder(){
        return encoder;
    }
    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }
}
