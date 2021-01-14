package com.tangmiyi.future.exampleorder.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 配置信息
 **/
public interface ExampleOrderMqProcess {

    String PROPERTIES_INPUT = "input";

    String PROPERTIES_OUTPUT = "output";

    /**
     * 消息接收
     *
     * @return
     */
    @Input(ExampleOrderMqProcess.PROPERTIES_INPUT)
    SubscribableChannel inputProperties();

    /**
     * 消息发送
     *
     * @return
     */
    @Output(ExampleOrderMqProcess.PROPERTIES_OUTPUT)
    MessageChannel outputProperties();
}
