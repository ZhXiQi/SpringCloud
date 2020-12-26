package com.springcloud.service.configuration.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author ZhXiQi
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //监听上下午变化事件，有变化则执行
        System.out.println("ApplicationListener<ContextRefreshedEvent> exec...");
    }
}
