package com.tangmiyi.future.exampleorder.client.service;

import com.tangmiyi.future.core.bean.PageBean;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.exampleorder.client.constant.UrlConstants;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface OrderPropertiesSnowService {


    /**
     * insert
     * @param propertiesSnowParam
     * @return
     */
    @PostMapping(UrlConstants.SERVICE_URL_PRE + "/add")
    @ApiOperation("新增propertiesSnow")
    ResultBean<Boolean> add(@Valid @RequestBody PropertiesSnowParam propertiesSnowParam);

    /**
     * 分页查询案例
     * @param propertiesSnowPageParam
     * @return
     */
    @PostMapping(UrlConstants.SERVICE_URL_PRE + "/pageList")
    @ApiOperation("分页查询propertiesSnow")
    ResultBean<PageBean> pageList(@Valid @RequestBody PropertiesSnowPageParam propertiesSnowPageParam);
}
