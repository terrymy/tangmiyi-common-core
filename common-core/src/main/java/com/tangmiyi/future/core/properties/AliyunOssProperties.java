package com.tangmiyi.future.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@Setter
@Getter
@RefreshScope
public class AliyunOssProperties {
    @Value("${ailiyun.oss.endpoint}")
    private String endpoint;

    @Value("${ailiyun.oss.bucketName}")
    private String bucketName;

    @Value("${ailiyun.oss.cdn}")
    private String ossCdn;

    @Value("${ailiyun.oss.keyId:}")
    private String keyId;

    @Value("${ailiyun.oss.keySecret:}")
    private String keySecret;
}
