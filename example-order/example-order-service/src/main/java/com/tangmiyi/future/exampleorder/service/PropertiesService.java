package com.tangmiyi.future.exampleorder.service;

import com.tangmiyi.future.exampleorder.pojo.param.TestValidParam;

public interface PropertiesService{

    void testExceptionReturn();

    void testRedisLock(Long id, String code, TestValidParam testValidParam);

    void testJetCache();
}
