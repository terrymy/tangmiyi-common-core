package com.tangmiyi.future.core.config;

import com.tangmiyi.future.core.utils.base.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 初始化加载系统常用资源
 */
@Component
@Order(1)
public class ThreadPoolConfig implements CommandLineRunner {

    @Value("${threadPoolExecutor.corePoolSize:15}")
    private Integer corePoolSize;

    @Value("${threadPoolExecutor.maximumPoolSize:30}")
    private Integer maximumPoolSize;

    @Value("${threadPoolExecutor.keepAliveTime:30}")
    private Integer keepAliveTime;


    @Override
    public void run(String... args) {
        // 初始线程池资源
        ThreadPoolUtils threadPoolUtils = ThreadPoolUtils.getInstance();
        ThreadPoolExecutor threadPoolExecutor = threadPoolUtils.getThreadPoolExecutor();
        if(threadPoolExecutor == null){
            // 线程队列使用SynchronousQueue
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,
                    new SynchronousQueue());
            threadPoolUtils.setThreadPoolExecutor(threadPoolExecutor);
        }
        threadPoolUtils.getThreadPoolInfo();
    }
}
