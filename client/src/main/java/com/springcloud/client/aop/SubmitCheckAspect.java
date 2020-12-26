package com.springcloud.client.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * @author ZhXiQi
 */
@Slf4j
@Component
@Aspect
public class SubmitCheckAspect {

    /**
     * 切指定注解
     */
    @Pointcut("execution(@com.springcloud.client.annotation.SubmitCheck * *(..))")
    public void SubmitCheck() {
    }

    private HashMap<String, String> requestMap = new HashMap<String, String>();


    /**
     * 接收到用户请求后，将请求和sessionID放入到map里头，如果再次提交表单，则直接报错
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("SubmitCheck()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arr = joinPoint.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String method = joinPoint.getSignature().getName();
        String sessionID = request.getSession().getId();
        if (requestMap.get(sessionID + method) != null) {
            log.error(method + "：表单重复提交");
            throw new Exception();
        } else {
            requestMap.put(sessionID + method, "doing");
        }
        Object ob = null;
        try {
            ob = joinPoint.proceed();
        } catch (Exception e) {
            requestMap.remove(sessionID + method);
            log.error(method + "：表单提交出错", e);
            throw e;
        }
        requestMap.remove(sessionID + method);
        return ob;
    }

    @Before("SubmitCheck()")
    public Object doBefore(ProceedingJoinPoint joinPoint) {
        return null;
    }

    @After("SubmitCheck()")
    public Object doAfter(ProceedingJoinPoint joinPoint) {
        return null;
    }

}
