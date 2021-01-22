package com.tangmiyi.future.examplegoods.rocketmq;

import com.tangmiyi.future.exampleorder.pojo.dto.MqPropertiesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * 配置订阅
 **/
@Service
@Slf4j
public class ExampleGoodsMpConsumer {

    @StreamListener(ExampleGoodsMqProcess.INPUT)
    public void receiveMessage(MqPropertiesDTO mqPropertiesDTO) {
        try {
            log.info("订阅配置消息id：" + mqPropertiesDTO.getId());
            Thread.sleep(300);
            // 这里处理实际业务
            // propertiesService.xxxxxxxx();
        } catch (Exception e) {
            log.error("订阅配置消息错误：", e);
            // 发送短信或者盯盯消息通知开发处理问题
        }
    }
}
