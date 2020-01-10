package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.rabbitmq.MQSender;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

//    @RequestMapping("/mq/header")
//    @ResponseBody
//    public Result<String> header() {
//        mqSender.sendHeader("hello Allen!");
//        return Result.success("hello,allen");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout() {
//        mqSender.sendFanout("hello Allen!");
//        return Result.success("hello,allen");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic() {
//        mqSender.sendTopic("hello Allen!");
//        return Result.success("hello,allen");
//    }
//
//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//        mqSender.send("hello Allen!");
//        return Result.success("hello,allen");
//    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,allen");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Allen");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }
    /*public Result<Long> redisGet() {
        Long value1 = redisService.get("key1", Long.class);
        return Result.success(value1);
    }*/

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("test1");
        boolean ret = redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }
    /*public Result<String> redisSet() {
        boolean ret = redisService.set("key2","allen");
        String str = redisService.get("key2", String.class);
        return Result.success(str);
    }*/
}
