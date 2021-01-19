package com.tangmiyi.future.exampleorder.xxjob;

import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;
import com.tangmiyi.future.exampleorder.service.PropertiesSnowService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoGlueJobHandler extends IJobHandler {

    @Autowired
    private PropertiesSnowService propertiesServiceSnow;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("propertiesServiceSnow start");
        PropertiesSnowParam propertiesSnowParam = new PropertiesSnowParam();
        propertiesSnowParam.setKey("A");
        propertiesSnowParam.setValue("A");
        propertiesSnowParam.setApplication("A");
        propertiesSnowParam.setProfile("A");
        propertiesSnowParam.setLable("A");
        propertiesSnowParam.setCommon(0);
        propertiesServiceSnow.insert(propertiesSnowParam);
        return ReturnT.SUCCESS;
    }

}
