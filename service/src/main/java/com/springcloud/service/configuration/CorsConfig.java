package com.springcloud.service.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 通过 FilterRegistrationBean 注册，实现Filter
 * @author ZhXiQi
 * @Title:
 * @date 2020/11/26 20:33
 */
@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        RegexCorsConfiguration config = new RegexCorsConfiguration();
        config.setAllowCredentials(true);
        // 设置你要允许的网站域名，如果全允许则设为 *
        config.addAllowedOrigin("https?://localhost");
        config.addAllowedOrigin("https?://localhost:\\d+");
        // 如果要限制 HEADER 或 METHOD 请自行更改
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        //使用 SpringBoot 自带的一个过滤器 CorsFilter 实现的跨域配置，如果需要进行 token 验证的话则需自定义实现一个 Filter 来进行注入。
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 配置需要过滤的url路径
        Collection<String> url = new LinkedList<>();
        url.add("/*");
        bean.setUrlPatterns(url);
        //注意：一个 FilterRegistrationBean 只能注册一个 Filter，通过 setOrder 方法控制执行顺序。
        // 这个顺序很重要哦，为避免麻烦请设置在最前
        bean.setOrder(0);
        return bean;
    }
}
