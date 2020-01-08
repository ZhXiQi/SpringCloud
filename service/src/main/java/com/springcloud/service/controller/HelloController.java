package com.springcloud.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 在这里我们使用RestController (等价于 @Controller 和 @ResponseBody)
 * @author ZhXiQi
 * @Title:
 * @date 2018/5/23 下午3:43
 */
@RestController
public class HelloController {
    /**
     * 在这里我们使用 @RequestMapping 建立请求映射：
     *      http://127.0.0.1:8080/hello
     * @return
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping(value = "/hi")
    public String hello2(){
        return "hello";
    }


}
