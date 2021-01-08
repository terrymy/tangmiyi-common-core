package com.tangmiyi.future.exampleorder.controller;

import com.tangmiyi.future.core.annotation.ServiceLogAop;
import com.tangmiyi.future.core.bean.PageBean;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;
import com.tangmiyi.future.exampleorder.service.PropertiesSnowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@ServiceLogAop
@Slf4j
public class PropertiesSnowController {

    @Autowired
    private PropertiesSnowService propertiesServiceSnow;

    /**
     * insert
     * @param propertiesSnowParam
     * @return
     */
    @PostMapping("/v1/propertiesSnow/add")
    public ResultBean<Boolean> add(@Valid @RequestBody PropertiesSnowParam propertiesSnowParam) {
        ResultBean<Boolean> resultBean = ResultBean.success();
        propertiesServiceSnow.insert(propertiesSnowParam);
        resultBean.setData(true);
        return resultBean;
    }

    /**
     * 分页查询案例
     * @param propertiesSnowPageParam
     * @return
     */
    @PostMapping("/v1/propertiesSnow/pageList")
    public ResultBean<PageBean> pageList(@Valid @RequestBody PropertiesSnowPageParam propertiesSnowPageParam) {
        ResultBean<PageBean> resultBean = ResultBean.success();
        resultBean.setData(propertiesServiceSnow.findPageList(propertiesSnowPageParam));
        return resultBean;
    }
}
