package com.springcloud.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZhXiQi
 * @Title: feignClient中的 path 参数为统一请求前缀
 * @date 2019/8/28 22:23
 */
@FeignClient(name = "clientOneAppName")
public interface FeignService {

    @GetMapping("/hi")
    String sayHiFromCilentOne(@RequestParam("name") String name);

}
