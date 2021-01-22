package com.tangmiyi.future.examplegoods.controller;

import com.tangmiyi.future.core.annotation.ServiceLogAop;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.core.enums.CommonEnabledEnum;
import com.tangmiyi.future.examplegoods.feign.ExampleOrderPropertiesSnowFeign;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ServiceLogAop
@Slf4j
public class FeignExampleController {

    @Autowired
    private ExampleOrderPropertiesSnowFeign exampleOrderPropertiesSnowFeign;

    /**
     * feign调用案例
     * @return
     */
    @GetMapping("/v1/feign/test")
    public ResultBean testFeign() {
        PropertiesSnowPageParam propertiesSnowPageParam = new PropertiesSnowPageParam();
        propertiesSnowPageParam.setCommon(CommonEnabledEnum.DISABLED.getValue());
        return exampleOrderPropertiesSnowFeign.pageList(propertiesSnowPageParam);
    }
}
