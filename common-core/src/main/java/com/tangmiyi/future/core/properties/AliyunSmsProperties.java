package com.tangmiyi.future.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@Setter
@Getter
@RefreshScope
public class AliyunSmsProperties {

    @Value("${ailiyun.sms.version:2017-05-25}")
    private String version;

    @Value("${ailiyun.sms.keyId:}")
    private String keyId;

    @Value("${ailiyun.sms.keySecret:}")
    private String keySecret;

    @Value("${ailiyun.sms.domain:dysmsapi.aliyuncs.com}")
    private String domain;

    @Value("${ailiyun.sms.signName:碧划算}")
    private String signName;

    @Value("${ailiyun.sms.checkTemplateId:SMS_168826710}")
    private String checkTemplateId;
}
