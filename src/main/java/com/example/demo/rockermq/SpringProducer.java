package com.example.demo.rockermq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lidaye
 */
@Component
public class SpringProducer {
    @Resource
    RocketMQTemplate rocketMqTemplate;

    public void sendMessage(String topic, String message) {
        rocketMqTemplate.convertAndSend(topic, message);
    }

}
