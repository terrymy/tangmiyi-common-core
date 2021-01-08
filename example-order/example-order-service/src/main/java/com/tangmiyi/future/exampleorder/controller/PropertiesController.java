package com.tangmiyi.future.exampleorder.controller;

import com.tangmiyi.future.core.annotation.ServiceLogAop;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.exampleorder.pojo.param.TestValidParam;
import com.tangmiyi.future.exampleorder.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@ServiceLogAop
@Slf4j
public class PropertiesController {

    @Autowired
    private PropertiesService propertiesService;

    /**
     * 参数校验案例
     * @param testValidParam
     * @return
     */
    @GetMapping("/v1/properties/testValid")
    public ResultBean<String> testValid(@Valid TestValidParam testValidParam) {
        ResultBean<String> resultBean = ResultBean.success();
        propertiesService.testExceptionReturn();
        resultBean.setData(testValidParam.getInputString());
        return resultBean;
    }

    /**
     * 参数校验案例，如果是post需要加@RequestBody
     * @param testValidParam
     * @return
     */
    @PostMapping("/v1/properties/testValid")
    @ServiceLogAop(logInfoOutput = false)
    public ResultBean<String> testPostValid(@Valid @RequestBody TestValidParam testValidParam) {
        ResultBean<String> resultBean = ResultBean.success();
        resultBean.setData(testValidParam.getInputString());
        return resultBean;
    }

    /**
     * testRedisLock案例
     * @return
     */
    @GetMapping("/v1/properties/testRedisLock")
    public ResultBean<Boolean> testRedisLock() {
        ResultBean<Boolean> resultBean = ResultBean.success();
        TestValidParam testValidParam = new TestValidParam();
        testValidParam.setInputFalse(true);
        testValidParam.setInputString("QQ");
        propertiesService.testRedisLock(1003L,"redisLock",testValidParam);
        resultBean.setData(true);
        return resultBean;
    }
}
