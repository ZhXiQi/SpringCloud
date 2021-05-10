package com.springcloud.service.configuration.intercept;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 调用错误的url请求时，会出现两次的拦截调用，原因是第二次的:org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.error
 * 也是一个controller路径为 /error
 *
 *
 * @author ZhXiQi
 * @Title:
 * @date 2019-07-15 14:24
 */
public class WebInterceptor implements HandlerInterceptor {

    /**
     * 在方法被调用前执行。在该方法中可以做类似校验的功能。
     * 如果返回true，则继续调用下一个拦截器。
     * 如果返回false，则中断执行，也就是说我们想调用的方法也 不会被执行，但是你可以修改response为你想要的响应。
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        System.out.println(method+" HandlerInterceptor preHandle");
        return true;
    }

    /**
     * 在方法执行后调用。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("HandlerInterceptor postHandle...");
    }

    /**
     * 在整个请求处理完毕后进行回调，也就是说视图渲染完毕或者调用方已经拿到响应。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("HandlerInterceptor afterCompletion...");
    }
}
