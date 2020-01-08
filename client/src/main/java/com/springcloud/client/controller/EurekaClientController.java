package com.springcloud.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-07-22 11:50
 */
@RestController
public class EurekaClientController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/hi")
    public String home(@RequestParam String name){
        return "HI "+name+", i am from port: "+port;
    }
}
