package com.springcloud.client.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import javax.servlet.ServletContext;

/**
 * @author ZhXiQi
 * @Title: 除了注解获取bean之外，其他获取Bean的几种方式
 * Bean工厂（com.springframework.beans.factory.BeanFactory）是Spring框架最核心的接口，它提供了高级IoC的配置机制。
 * BeanFactory使管理不同类型的Java对象成为可能，应用上下文（com.springframework.context.ApplicationContext）建立在BeanFactory基础之上，提供了更多面向应用的功能，它提供了国际化支持和框架事件体系，更易于创建实际应用。
 * 一般称BeanFactory为IoC容器，而称ApplicationContext为应用上下文
 *
 * BeanFactory是Spring框架的基础设施，面向Spring本身；
 * ApplicationContext面向使用Spring框架的开发者，几乎所有的应用场合我们都直接使用ApplicationContext而非底层的BeanFactory。
 *
 * BeanFactory是Spring框架的基础设施，面向Spring本身；
 * ApplicationContext面向使用Spring框架的开发者，几乎所有的应用场合我们都直接使用ApplicationContext而非底层的BeanFactory。
 *
 * 要手动获取Bean，最关键的是获取 com.springframework.context.ApplicationContext，除了注解方式，还有以下几种：
 * 方法一：在初始化时保存ApplicationContext对象
 * 方法二：通过Spring提供的utils类获取ApplicationContext对象
 * 方法三：继承自抽象类ApplicationObjectSupport
 * 方法四：继承自抽象类WebApplicationObjectSupport
 * 方法五：实现接口ApplicationContextAware
 * 方法六：通过Spring提供的ContextLoader
 *
 * @date 2021/3/26 10:09
 */
public class LoadBeanUtils {
    /**
     * 方法一：在初始化时保存ApplicationContext对象
     * 这种方式适用于采用Spring框架的独立应用程序，需要程序通过配置文件手工初始化Spring的情况
     */
    public Object loadBeanFuncOne(String beanName, String xmlName) {
        ApplicationContext ac = new FileSystemXmlApplicationContext(xmlName);
        Object bean = ac.getBean(beanName);
        return bean;
    }

    /**
     * 方法二：通过Spring提供的工具类获取ApplicationContext对象
     * 这种方式适合于采用Spring框架的B/S系统，通过ServletContext对象获取ApplicationContext对象，然后在通过它获取需要的类实例。
     * 上面两个工具方式的区别是，前者在获取失败时抛出异常，后者返回null。
     *
     * ServletContext 获取方式：
     * 1.request获取servletContext : ServletContext servletContext = request.getServletContext();
     * 2.使用ContextLoader : ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
     * 3.使用spring注入自动注入 :
     *                  @Autowired
     *                  private ServletContext servletContext;
     *
     * 注意：如果需要在spring容器初始化时读取servletContext中的值，那么servletContext值的初始化的servletContextListener一定要在org.springframework.web.context.ContextLoaderListener之前加载
     *
     * @return
     */
    public Object loadBeanFuncTwo(String beanId) {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        ApplicationContext ac2 = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        Object bean = ac.getBean(beanId);
        Object bean1 = ac2.getBean(beanId);
        return bean;
    }

    /**
     * 抽象类ApplicationObjectSupport提供getApplicationContext()方法，可以方便的获取ApplicationContext。
     *
     * Spring初始化时，会通过该抽象类的setApplicationContext(ApplicationContext context)方法将ApplicationContext 对象注入。
     */
    class LoadBeanThree extends ApplicationObjectSupport {

        public Object loadBeanFuncThree(String beanId) {
            return super.getApplicationContext().getBean(beanId);
        }
    }

    /**
     * 与 LoadBeanThree 类似
     */
    class LoadBeanFour extends WebApplicationObjectSupport {
        public Object loadBeanFuncFour(String beanId) {
            super.getApplicationContext().getBean(beanId);
            return super.getWebApplicationContext().getBean(beanId);
        }
    }

    /**
     * SpringContextUtil
     * 方法五：实现接口ApplicationContextAware
     * 实现该接口的setApplicationContext(ApplicationContext context)方法，并保存ApplicationContext 对象。Spring初始化时，会通过该方法将ApplicationContext对象注入
     */
    class LoadBeanFive implements ApplicationContextAware {
        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        public Object loadBeanFuncFive(String beanId) {
            Object bean = this.applicationContext.getBean(beanId);
            return bean;
        }
    }

    /**
     * 方法六：通过Spring提供的ContextLoader
     * 这种不依赖于servlet,不需要注入的方式。但是需要注意一点，在服务器启动时，Spring容器初始化时，不能通过以下方法获取Spring容器
     * @param beanId
     * @return
     */
    public Object loadBeanFuncSix(String beanId) {
        Object bean = ContextLoader.getCurrentWebApplicationContext().getBean(beanId);
        return bean;
    }
}
