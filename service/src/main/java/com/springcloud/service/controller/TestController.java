package com.springcloud.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/12/1 13:27
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String hello() {
        return "/error.html";
    }
}
