package com.springcloud.service.configuration.filter;

import com.springcloud.service.utils.AESUtil;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * filter（过滤器）作用于在intreceptor(拦截器)之前，不像intreceptor一样依赖于springmvc框架，filter只需要依赖于serverlet
 *
 * 2.拦截器与Filter的区别
 * Spring的拦截器与Servlet的Filter有相似之处，比如二者都是AOP编程思想的体现，都能实现权限检查、日志记录等。不同的是：
 * 使用范围不同：Filter是Servlet规范规定的，只能用于Web程序中。而拦截器既可以用于Web程序，也可以用于Application、Swing程序中。
 * 规范不同：Filter是在Servlet规范中定义的，是Servlet容器支持的。而拦截器是在Spring容器内的，是Spring框架支持的。
 * 使用的资源不同：同其他的代码块一样，拦截器也是一个Spring的组件，归Spring管理，配置在Spring文件中，因此能使用Spring里的任何资源、对象，例如Service对象、数据源、事务管理等，通过IoC注入到拦截器即可；而Filter则不能。
 * 深度不同：Filter在只在Servlet前后起作用。而拦截器能够深入到方法前后、异常抛出前后等，因此拦截器的使用具有更大的弹性。所以在Spring构架的程序中，要优先使用拦截器。
 *
 * 实际上Filter和Servlet极其相似，区别只是Filter不能直接对用户生成响应。实际上Filter里doFilter()方法里的代码就是从多个Servlet的service()方法里抽取的通用代码，通过使用Filter可以实现更好的复用。
 * filter是一个可以复用的代码片段，可以用来转换HTTP请求、响应和头信息。Filter不像Servlet，它不能产生一个请求或者响应，它只是修改对某一资源的请求，或者修改从某一的响应。
 * JSR中说明的是，按照多个匹配的Filter，是按照其在web.xml中配置的顺序来执行的。
 * 所以这也就是，把自己的Filter或者其他的Filter（比如UrlRewrite的Filter）放在Struts的DispatcherFilter的前面的原因。因为，它们需要在请求被Struts2框架处理之前，做一些前置的工作。
 * 当Filter被调用，并且进入了Struts2的DispatcherFilter中后，Struts2会按照在Action中配置的Interceptor Stack中的Interceptor的顺序，来调用Interceptor。
 *
 * @author ZhXiQi
 * @Title:
 * @date 2019-07-15 14:17
 *
 * 主要是通过 @WebFilter 配置需要过滤的URL校验正则，然后需要在启动类上添加注解 @ServletComponentScan
 * 如果在filter类上添加了@Component或@Configuration，又添加了@WebFilter()，那么会初始化两次Filter，并且会过滤所有路径+自己指定的路径 ，便会出现对没有指定的URL也会进行过滤
 *
 */
@WebFilter(filterName = "MyWebFilter",urlPatterns = {"/v1/*"})
public class MyWebFilter implements Filter {

    @Value(value = "${aes.key}")
    private String AES_KEY;

    /**
     * 先执行初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     *
     * @param servletRequest 处理request请求过滤
     * @param servletResponse 处理response请求过滤
     * @param filterChain 拦截器链，最后必须要调用下一个拦截器链中的下一个filter
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("filter servletRequest"+servletRequest.getContentType());
        String requestURI = ((RequestFacade) servletRequest).getRequestURI();
        if (requestURI.contains("v1")) {
            throw new RuntimeException();
        }
        /*
        String productId = servletRequest.getParameter("productId");
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        try {
            String decrypt = AESUtil.decrypt(productId, AES_KEY);
            parameterMap.put("productId",new String[]{decrypt});
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestFacade requestFacade = (RequestFacade) servletRequest;

        try {
            Field request = requestFacade.getClass().getDeclaredField("request");
            request.setAccessible(true);
            Class<?> type = request.getType();

            Field parameterMapField = type.getDeclaredField("parameterMap");
            parameterMapField.setAccessible(true);
            parameterMapField.set(type,parameterMap);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(productId);
        filterChain.doFilter(servletRequest,servletResponse);
        */
        System.out.println("filter servletResponse:"+servletResponse.getContentType());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
