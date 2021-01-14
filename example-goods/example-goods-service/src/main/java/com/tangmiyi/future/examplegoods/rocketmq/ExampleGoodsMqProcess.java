package com.tangmiyi.future.examplegoods.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 配置信息
 **/
public interface ExampleGoodsMqProcess {

    String INPUT = "input";

    /**
     * 消息接收
     *
     * @return
     */
    @Input(ExampleGoodsMqProcess.INPUT)
    SubscribableChannel inputProperties();
}
