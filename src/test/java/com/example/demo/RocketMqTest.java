package com.example.demo;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RocketMqTest {
    @Resource
    RocketMQTemplate rocketMqTemplate;

    @Test
    public void producerTest() {
        rocketMqTemplate.convertAndSend("topic:tag", "Hello, Lxk!");

    }

    @Test
    public void consumerTest() {
    }


}
