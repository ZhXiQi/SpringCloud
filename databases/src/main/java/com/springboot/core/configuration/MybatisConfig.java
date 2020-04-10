package com.springboot.core.configuration;

import com.springboot.core.handler.LocalDateTimeTypeHandler;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019/11/20 14:09
 */
@Configuration
public class MybatisConfig {

    @Value("${jdbc.driver-class-name}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public PropertiesFactoryBean configProperties() throws Exception{
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        propertiesFactoryBean.setLocations(resolver.getResources("classpath*:application.yml"));
        return propertiesFactoryBean;
    }

    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(30);
        dataSource.setInitialSize(10);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //xml文件放置位置
        Resource[] mapperResource = resolver.getResources("classpath*:sqlmap/**/*.xml");
        List<Resource> resourceList = new ArrayList<>();
        Collections.addAll(resourceList);
        Collections.addAll(resourceList,mapperResource);
        Resource[] newResources = resourceList.toArray(new Resource[resourceList.size()]);
        sqlSessionFactoryBean.setMapperLocations(newResources);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.springboot.core.bean.auto.*");
        //添加自定义TypeHandler
        sqlSessionFactoryBean.setTypeHandlers(new LocalDateTimeTypeHandler());
        //下划线转驼峰
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(sqlSessionFactoryBean.getObject().getConfiguration());
        //设置自己的拦截器,必须要以数组的形式，否则会使mybatis自己的一些拦截器失效
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{  });

        return sqlSessionFactoryBean;
    }
}
