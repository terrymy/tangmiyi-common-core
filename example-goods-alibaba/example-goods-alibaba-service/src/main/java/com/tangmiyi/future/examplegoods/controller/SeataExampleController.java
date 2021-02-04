package com.tangmiyi.future.examplegoods.controller;

import com.tangmiyi.future.core.annotation.ServiceLogAop;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.examplegoods.service.SeataExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ServiceLogAop
@Slf4j
public class SeataExampleController {

    @Autowired
    private SeataExampleService seataExampleService;

    /**
     * 分布式事务测试案例
     * @return
     */
    @GetMapping("/v1/seata/test")
    public ResultBean<Boolean> testSeata() {
        ResultBean<Boolean> resultBean = ResultBean.success();
        seataExampleService.testSeata();
        resultBean.setData(true);
        return resultBean;
    }
}
