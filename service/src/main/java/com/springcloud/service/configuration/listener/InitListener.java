package com.springcloud.service.configuration.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/11/26 19:26
 */
@Component
public class InitListener implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //项目启动时运行
        System.out.println("CommandLineRunner exec...");
    }
}
