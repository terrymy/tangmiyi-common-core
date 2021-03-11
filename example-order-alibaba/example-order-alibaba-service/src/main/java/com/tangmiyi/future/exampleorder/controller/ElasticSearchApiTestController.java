package com.tangmiyi.future.exampleorder.controller;

import com.tangmiyi.future.core.annotation.ServiceLogAop;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.exampleorder.service.ElasticSearchApiTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@ServiceLogAop
@Slf4j
public class ElasticSearchApiTestController {

    @Autowired
    private ElasticSearchApiTestService elasticSearchApiTestService;

    /**
     * 创建索引库
     * @return
     */
    @GetMapping("/v1/esIndex/create")
    public ResultBean<Boolean> testValid() throws IOException {
        ResultBean<Boolean> resultBean = ResultBean.success();
        elasticSearchApiTestService.createIndex();
        resultBean.setData(true);
        return resultBean;
    }
}
