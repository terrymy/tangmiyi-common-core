package com.tangmiyi.future.exampleorder.rocketmq;

import com.tangmiyi.future.exampleorder.pojo.dto.MqPropertiesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * 配置订阅
 **/
@Service
@Slf4j
public class ExampleOrderMqConsumer {

//    @StreamListener(PropertiesProcess.INPUT)acknowledgeMode为MANUAL表示手动模式时案例代码
//    public void receiveMessage(MqPropertiesDTO mqPropertiesDTO, @Header(AmqpHeaders.CHANNEL) Channel channel,
//                               @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
//        log.info("订阅配置消息：" + mqPropertiesDTO);
//        try {
//            // 这里处理实际业务
//            // propertiesService.xxxxxxxx();
//            channel.basicAck(deliveryTag, false);
//        } catch (Exception e) {
//            log.error("订阅配置消息错误：", e);
//            channel.basicNack(deliveryTag, false, false);
//        }
//    }

    @StreamListener(ExampleOrderMqProcess.PROPERTIES_INPUT)
    public void receiveMessage(@Payload MqPropertiesDTO mqPropertiesDTO) {
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
