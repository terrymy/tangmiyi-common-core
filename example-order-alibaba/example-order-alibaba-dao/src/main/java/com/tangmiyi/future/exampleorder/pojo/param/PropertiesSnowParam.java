package com.tangmiyi.future.exampleorder.pojo.param;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertiesSnowParam {

    private static final long serialVersionUID = 1L;

    /**
     * 配置文件key
     */
    private String key;

    /**
     * 配置文件value
     */
    private String value;

    /**
     * 客户端code
     */
    private String application;

    /**
     * 环境激活
     */
    private String profile;

    /**
     * 分支
     */
    private String lable;

    /**
     * 是否公用，1是，0否
     * @see com.tangmiyi.future.core.enums.CommonEnabledEnum
     */
    private Integer common;
}