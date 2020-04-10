package com.springcloud.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZhXiQi
 * @Title: feignClient中的 path 参数为统一请求前缀
 * 定义一个feign接口，通过@ FeignClient（“服务名”），来指定调用哪个服务。比如在代码中调用了service-hi服务的“/hi”接口，@FeignClient 只能用于接口
 * name(value)为服务提供者向注册中心注册的实例名
 * @date 2019/8/28 22:23
 */
@FeignClient(name = "clientOneAppName")
public interface FeignService {

    @GetMapping("/hi")
    String sayHiFromCilentOne(@RequestParam("name") String name);

}
