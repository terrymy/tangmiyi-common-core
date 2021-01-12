package com.tangmiyi.future.exampleorder.service.impl;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.tangmiyi.future.core.annotation.RedissonLock;
import com.tangmiyi.future.core.exception.BusinessException;
import com.tangmiyi.future.exampleorder.consts.ExampleOrderJetCacheConstants;
import com.tangmiyi.future.exampleorder.consts.ExampleOrderReturnConstants;
import com.tangmiyi.future.exampleorder.pojo.param.TestValidParam;
import com.tangmiyi.future.exampleorder.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PropertiesServiceImpl implements PropertiesService {

    @CreateCache(name = ExampleOrderJetCacheConstants.PROPERTIES)
    protected Cache<String, String> cache;

    @Override
    public void testExceptionReturn() {
        throw new BusinessException(ExampleOrderReturnConstants.TEST_CHAR,"哈哈");
    }

    @Override
    @RedissonLock(lockIndex = 2)
    public void testRedisLock(Long id, String code, TestValidParam testValidParam) {
        log.info("testRedisLock--->{},{},{}",id,code,testValidParam.getInputString());
    }

    @Override
    public void testJetCache() {
        cache.put("testJetCache:" + 1000L, "cache api", ExampleOrderJetCacheConstants.PROPERTIES_EXPIRE, TimeUnit.SECONDS);
        String cacheApi = cache.get("testJetCache:" + 1000L);
        log.info("get testJetCache {}",cacheApi);
    }
}
