package com.xyl.mydelivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.mydelivery.entity.User;

public interface UserService extends IService<User> {

    public void sendMsg(String to,String subject,String context);
}
