package com.springcloud.client.service;

import com.springcloud.client.conf.FeignConfig;
import com.springcloud.client.service.fallback.AttachServerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhXiQi
 * @Title: feign调用附件服务（微服务客户端配合feign使用（ribbon同理））
 * controller层安装常规的写法书写，并调用此层来进行 feign 调用
 * feignClient中的 path 参数为统一请求前缀
 * 定义一个feign接口，通过@ FeignClient（“服务名”），来指定调用哪个服务。比如在代码中调用了service-hi服务的“/hi”接口，@FeignClient 只能用于接口
 * name(value)为服务提供者向注册中心注册的实例名
 * @date 2019/8/27 19:48
 */
@FeignClient(value = "attachment", path = "/v1/attachment", configuration = FeignConfig.class, fallback = AttachServerFallback.class)
public interface AttachServer {

    @PostMapping("")
    Object uploadFile(
            @RequestPart(value = "file") MultipartFile files,
            @RequestParam(required = false, defaultValue = "xxx") String bucketName,
            @RequestParam(required = false) Integer type
    );

    @PostMapping("/server")
    String uploadFileServer(
            @RequestPart(value = "file") MultipartFile files,
            @RequestParam(required = false, defaultValue = "xxx") String folder,
            @RequestParam(required = false) Integer type
    );

    @PostMapping("/all")
    Object uploadFileAll(
            @RequestPart(value = "file") MultipartFile files,
            @RequestParam(required = false, defaultValue = "xxx") String folder,
            @RequestParam(required = false) Integer type
    );

    @PostMapping("/local")
    Object uploadFileLocal(
            @RequestParam("path") String path,
            @RequestParam("fileName") String fileName,
            @RequestParam("folder") String folder
    );

    @PostMapping("/local2")
    Object uploadFileLocal(
            @RequestPart(value = "file") MultipartFile file,
            @RequestParam("folder") String folder,
            @RequestParam("type") Integer type
    );
}
