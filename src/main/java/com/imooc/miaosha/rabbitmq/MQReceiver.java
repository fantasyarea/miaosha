package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private static Logger log = Logger.getLogger(MQReceiver.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String messsage){
        log.info("receive message:" + messsage);
        MiaoshaMessage mm = RedisService.stringToBean(messsage, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            return ;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null){
            return;
        }

        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }

    /**
     *  Direct 模式 交换机Exchange
     */
//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String messsage){
//        log.info("receive message:" + messsage);
//    }
//
//    /**
//     *  Topic 模式 交换机Exchange
//     */
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String messsage){
//        log.info("topic queue1 message:" + messsage);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String messsage){
//        log.info("topic queue2 message:" + messsage);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADER_QUEUE1)
//    public void receiveHeader(byte[] messsage){
//        log.info("topic header message:" + new String(messsage));
//    }

}
