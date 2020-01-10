package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    RedisService redisService;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User u1 = new User();
        u1.setId(2);
        u1.setName("kay");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("wence");
        userDao.insert(u2);
        return true;
    }
}
