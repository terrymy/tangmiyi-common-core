package com.tangmiyi.future.examplegoods.feign;

import com.tangmiyi.future.examplegoods.feign.fallback.ExampleOrderPropertiesSnowFeignFallback;
import com.tangmiyi.future.exampleorder.client.service.OrderPropertiesSnowService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "example-order-alibaba",fallback = ExampleOrderPropertiesSnowFeignFallback.class)
public interface ExampleOrderPropertiesSnowFeign extends OrderPropertiesSnowService {

}
