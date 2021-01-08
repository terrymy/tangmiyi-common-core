package com.tangmiyi.future.examplegoods.feign;

import com.tangmiyi.future.exampleorder.client.service.OrderPropertiesSnowService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "example-order")
public interface ExampleOrderPropertiesSnowFeign extends OrderPropertiesSnowService {

}
