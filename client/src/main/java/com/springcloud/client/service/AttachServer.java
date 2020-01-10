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
 * @date 2019/8/27 19:48
 */
@FeignClient(value = "attachment", path = "/v1/attachment", configuration = FeignConfig.class, fallback = AttachServerFallback.class)
public interface AttachServer {

    @PostMapping("")
    Object uploadFile(
            @RequestPart(value = "file") MultipartFile files,
            @RequestParam(required = false, defaultValue = "filoink") String bucketName,
            @RequestParam(required = false) Integer type
    );

    @PostMapping("/server")
    String uploadFileServer(
            @RequestPart(value = "file") MultipartFile files,
            @RequestParam(required = false, defaultValue = "filoink") String folder,
            @RequestParam(required = false) Integer type
    );

    @PostMapping("/all")
    Object uploadFileAll(
            @RequestPart(value = "file") MultipartFile files,
            @RequestParam(required = false, defaultValue = "filoink") String folder,
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
