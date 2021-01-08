package com.tangmiyi.future.exampleorder.service.impl;


import com.tangmiyi.future.core.annotation.RedissonLock;
import com.tangmiyi.future.core.exception.BusinessException;
import com.tangmiyi.future.exampleorder.consts.ExampleOrderReturnConstants;
import com.tangmiyi.future.exampleorder.pojo.param.TestValidParam;
import com.tangmiyi.future.exampleorder.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PropertiesServiceImpl implements PropertiesService {

    @Override
    public void testExceptionReturn() {
        throw new BusinessException(ExampleOrderReturnConstants.TEST_CHAR,"哈哈");
    }

    @Override
    @RedissonLock(lockIndex = 2)
    public void testRedisLock(Long id, String code, TestValidParam testValidParam) {
        log.info("testRedisLock--->{},{},{}",id,code,testValidParam.getInputString());
    }
}
