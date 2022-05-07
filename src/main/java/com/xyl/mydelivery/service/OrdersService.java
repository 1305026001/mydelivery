package com.xyl.mydelivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.mydelivery.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
