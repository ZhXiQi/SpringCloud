package com.springcloud.client.service.fallback;

import com.springcloud.client.service.AttachServer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhXiQi
 * @Title: feign请求失败的逻辑处理
 * @date 2019/8/29 14:42
 */
@Service
public class AttachServerFallback implements AttachServer {
    @Override
    public Object uploadFile(MultipartFile files, String bucketName, Integer type) {
        //失败逻辑处理
        return null;
    }

    @Override
    public String uploadFileServer(MultipartFile files, String folder, Integer type) {
        //失败逻辑处理
        return null;
    }

    @Override
    public Object uploadFileAll(MultipartFile files, String folder, Integer type) {
        //失败逻辑处理
        return null;
    }

    @Override
    public Object uploadFileLocal(String path, String fileName, String folder) {
        //失败逻辑处理
        return null;
    }

    @Override
    public Object uploadFileLocal(MultipartFile file, String folder, Integer type) {
        //失败逻辑处理
        return null;
    }
}
