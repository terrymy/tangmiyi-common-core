package com.tangmiyi.future.exampleorder.xxjob;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoGlueJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        log.info("XXL-JOB, ok"+param);
        return ReturnT.SUCCESS;
    }
}
