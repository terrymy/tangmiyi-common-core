package com.tangmiyi.future.core.thread;

import brave.Tracer;
import com.aliyun.oss.OSSClient;
import com.tangmiyi.future.core.properties.AliyunOssProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
@AllArgsConstructor
public class AliyunOssUploadThread implements Runnable{

    private OSSClient ossClient;

    private InputStream inputStream;

    private String key;

    private AliyunOssProperties aliyunOssProperties;

    private Tracer tracer;

    @Override
    public void run() {
        try {
            ossClient.putObject(aliyunOssProperties.getBucketName(), key, inputStream);
        }
        catch (Exception e) {
            log.error("Aliyun.oss.upload.error:",e);
            tracer.currentSpan().tag("Aliyun.oss.upload.error", e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }
}
