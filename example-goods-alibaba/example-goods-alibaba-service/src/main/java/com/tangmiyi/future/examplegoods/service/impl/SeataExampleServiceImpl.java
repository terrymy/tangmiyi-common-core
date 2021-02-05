package com.tangmiyi.future.examplegoods.service.impl;


import com.tangmiyi.future.examplegoods.dao.XxlJobUserMapper;
import com.tangmiyi.future.examplegoods.feign.ExampleOrderPropertiesSnowFeign;
import com.tangmiyi.future.examplegoods.pojo.XxlJobUserDO;
import com.tangmiyi.future.examplegoods.service.SeataExampleService;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SeataExampleServiceImpl implements SeataExampleService {

    @Autowired
    private ExampleOrderPropertiesSnowFeign exampleOrderPropertiesSnowFeign;

    @Resource
    private XxlJobUserMapper xxlJobUserMapper;

    @Override
    @GlobalTransactional(timeoutMills = 100000, name = "spring-cloud-demo-tx")
    public void testSeata() {
        XxlJobUserDO xxlJobUserDO = new XxlJobUserDO();
        xxlJobUserDO.setId(2L);
        xxlJobUserDO.setUsername("seata");
        xxlJobUserDO.setPassword("xxxxxlllllll");
        xxlJobUserDO.setRole(1);
        xxlJobUserMapper.insert(xxlJobUserDO);
        // do someting....
        PropertiesSnowParam propertiesSnowParam = new PropertiesSnowParam();
        propertiesSnowParam.setKey("A");
        propertiesSnowParam.setApplication("A");
        propertiesSnowParam.setCommon(0);
        propertiesSnowParam.setLable("A");
        propertiesSnowParam.setValue("A");
        propertiesSnowParam.setProfile("A");
        exampleOrderPropertiesSnowFeign.add(propertiesSnowParam);
        int i=1/0;
    }
}
