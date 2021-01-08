package com.tangmiyi.future.exampleorder.service;

import com.tangmiyi.future.core.bean.PageBean;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;

public interface PropertiesSnowService {

    /**
     * 分页查询
     * @return
     */
    PageBean findPageList(PropertiesSnowPageParam propertiesSnowPageParam);

    /**
     * 新增
     * @param propertiesSnowParam
     * @return
     */
    void insert(PropertiesSnowParam propertiesSnowParam);
}
