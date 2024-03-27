package com.example.demo.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class uploadUtil {
    // 域名
    public static final String Ali_DOMAIN = "https://阿里云远程地址/";

    public static final String uploadImage(MultipartFile file) throws IOException {
        // 生成文件名
        String originlFilename = file.getOriginalFilename();
        String ext = "."  + FilenameUtils.getExtension(originlFilename);
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;
        // 地域节点 （阿里云）
        String endpoint = "地域节点";
        String accessKeyId = "";
        String accessKeySecret = ""; // 需要自行获取
        // OSS客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(
                "", //仓库名
                fileName, //文件名
                file.getInputStream()
        );
        ossClient.shutdown();
        return Ali_DOMAIN + fileName;
    }



}
