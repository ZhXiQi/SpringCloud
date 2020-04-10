/*
package com.springboot.core.util;


import com.springboot.core.configuration.OssConfig;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

*/
/**
 *
 *//*

@Slf4j
@Component
public class OssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String BUCKET_NAME;
    private String cdnSite;

    private OSSClient ossClient;

    @Resource
    private OssConfig ossConfig;


    @PostConstruct
    public void init() {
        this.endpoint = ossConfig.getEndpoint();
        this.accessKeyId = ossConfig.getAccessKeyId();
        this.accessKeySecret = ossConfig.getAccessKeySecret();
        this.BUCKET_NAME = ossConfig.getBucketName();
        this.cdnSite = ossConfig.getCdnSite();
        if (ossClient == null) {
            ossClient = new OSSClient(
                    endpoint, accessKeyId, accessKeySecret);
        }
    }

    public String getDefaultBucket() {
        return BUCKET_NAME;
    }

    // 创建桶
    public void createBucket(String bucketName) {
        // 创建存储空间。
        ossClient.createBucket(bucketName);
    }
    public String getCdnSite()
    {
        return cdnSite;
    }
    //列出所有bucket
    public List<Bucket> listBucket() {
        return ossClient.listBuckets();
    }


    // 保存文件
    public void saveFile(File file, String bucketName, String objectName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucketName, objectName, inputStream);
        //如果不需要File文件可删除
        if (file.exists()) {
            boolean ret = file.delete();
        }
    }

    // 保存文件
    public String saveFileDefault(File file, String objectName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ossClient.putObject(BUCKET_NAME, objectName, inputStream);
        //如果不需要File文件可删除
        if (file.exists()) {
            boolean ret = file.delete();
        }
        return getFileUrl(BUCKET_NAME, objectName);
    }


    //TODO CDN配置化
    public String getFileUrl(String bucketName, String objectName) {
        String prefix = objectName.substring(objectName.lastIndexOf("."));

        String url = StringUtils.isEmpty(cdnSite) ?
                "http://" + bucketName + "." + endpoint + File.separator + objectName :
                cdnSite + objectName;
        //暂时不对图片预览做限制
        //if (prefix.equals(".jpg") || prefix.equals(".JPG") || prefix.equals(".jpeg") || prefix.equals(".png")) {
            //url = url + "?x-oss-process=image/auto-orient,1";
        //}
        return url;
    }
    public String getDefaultFileUrl( String objectName) {
//        String prefix = objectName.substring(objectName.lastIndexOf("."));

        String url = StringUtils.isEmpty(cdnSite) ?
                "http://" + BUCKET_NAME + "." + endpoint + File.separator + objectName :
                cdnSite + objectName;
        //暂时不对图片预览做限制
        //if (prefix.equals(".jpg") || prefix.equals(".JPG") || prefix.equals(".jpeg") || prefix.equals(".png")) {
            //url = url + "?x-oss-process=image/auto-orient,1";
        //}
        log.info("buckct name: {}",BUCKET_NAME);
        return url;
    }
    //TODO 文件类型生成方式
    public String createFlieName(String originFileName, String resourceType) throws UnsupportedEncodingException {
        String prefix = originFileName.substring(originFileName.lastIndexOf('.'));
        return DigestUtils.md5Hex(originFileName + resourceType + System.currentTimeMillis()) + prefix;
    }


    public OSSObject downLoadFile(String objectName) {
        OSSObject object = ossClient.getObject(BUCKET_NAME, objectName);;
        return object;
    }
}
*/
