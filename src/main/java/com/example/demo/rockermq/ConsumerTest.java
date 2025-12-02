package com.example.demo.rockermq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lidaye
 */
public class ConsumerTest {
    private static final Logger logger = LoggerFactory.getLogger(PushConsumerExample.class);

    public static void main(String[] args) throws Exception {
        // 设置NameServer地址(需与生产者一致)
        String namesrvAddr = "192.168.3.140:9876";

        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setNamesrvAddr(namesrvAddr);
        //广播模式
//        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 订阅Topic和Tag(需与生产者一致)
        consumer.subscribe("Lxkssg", "*");

        // 注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs,
                    ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {
                    logger.info("收到消息: {}",new String(msg.getBody()));
                    logger.info("Consume message successfully, messageId={}", msg.getMsgId());

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();
        logger.info("消费者已启动，等待消息...");

        // 保持运行
        TimeUnit.MINUTES.sleep(5);

        // 关闭消费者
        consumer.shutdown();
    }
}
