package com.tangmiyi.future.exampleorder.pojo;

import com.tangmiyi.future.core.pojo.base.BaseDO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "properties_snow")
@Setter
@Getter
public class PropertiesSnowDO extends BaseDO {

    /**
     * 配置文件key
     */
    @Column(name = "`key`")
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