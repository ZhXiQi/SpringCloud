package com.springcloud.service.configuration.listener;

import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/11/26 19:30
 */
@WebListener
@Component
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //session创建时调用
        System.out.println("sessionCreated...");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //session失效时调用
        System.out.println("sessionDestroyed...");
    }
}
