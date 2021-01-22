package com.tangmiyi.future.examplegoods;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.tangmiyi.future.examplegoods.rocketmq.ExampleGoodsMqProcess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages= {"com.tangmiyi.future"})
@EnableHystrix
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.tangmiyi.future.*.dao")
@ComponentScan(basePackages = {"com.tangmiyi.future"})
@EnableMethodCache(basePackages = {"com.tangmiyi.future"})
@EnableCreateCacheAnnotation
@EnableBinding({ExampleGoodsMqProcess.class})
public class ExampleGoodsAlibabaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleGoodsAlibabaApplication.class, args);
    }
}
