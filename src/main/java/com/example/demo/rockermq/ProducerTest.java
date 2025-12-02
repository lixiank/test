package com.example.demo.rockermq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author lidaye
 */
public class ProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(ProducerExample.class);

    public static void main(String[] args) throws Exception {
        // 设置NameServer地址
        String namesrvAddr = "192.168.3.140:9876"; // 替换为你的Docker主机IP

        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("example_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr(namesrvAddr);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i <10 ; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("Lxkssg", "TagA", ("Hello RocketMQ " + i).getBytes(StandardCharsets.UTF_8));
            msg.setDelayTimeSec(10);
            // 发送消息
           producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("Send message successfully, messageId={}", sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    logger.error("Send message failed", e);
                }
            });
        }


        // 如果不再发送消息，关闭Producer实例
//        producer.shutdown();
    }
}
