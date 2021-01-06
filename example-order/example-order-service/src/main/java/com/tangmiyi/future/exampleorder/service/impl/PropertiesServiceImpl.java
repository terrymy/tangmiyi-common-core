package com.tangmiyi.future.exampleorder.service.impl;


import com.tangmiyi.future.core.exception.BusinessException;
import com.tangmiyi.future.exampleorder.consts.ExampleOrderReturnConstants;
import com.tangmiyi.future.exampleorder.service.PropertiesService;
import org.springframework.stereotype.Service;

@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Override
    public void testExceptionReturn() {
        throw new BusinessException(ExampleOrderReturnConstants.TEST_CHAR,"哈哈");
    }
}
