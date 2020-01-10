package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.redis.RedisService;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    private static Logger log = Logger.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMiaoshaMessage(MiaoshaMessage mm) {
        String msg = RedisService.beanToString(mm);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }

    /*public void send(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send first topic message:" + msg + "1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");

        log.info("send second topic message:" + msg + "2");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }

    public void sendFanout(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send first topic message:" + msg + "1");
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "topic.key1", msg+"1");

        log.info("send second topic message:" + msg + "2");
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "topic.key2", msg+"2");
    }

    public void sendHeader(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send first topic message:" + msg );
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "values1");
        properties.setHeader("header2", "values2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
    }*/
}
