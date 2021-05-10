package com.springcloud.feign.controller;

import com.springcloud.feign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-07-22 21:08
 */
@RestController
public class HiController {

    @Autowired
    private FeignService schedualServiceHi;

    @GetMapping("/hi")
    public String sayHi(@RequestParam String name){
        return schedualServiceHi.sayHiFromCilentOne(name);
    }
}
