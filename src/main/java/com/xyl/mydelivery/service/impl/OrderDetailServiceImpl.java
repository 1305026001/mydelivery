package com.xyl.mydelivery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.mydelivery.entity.OrderDetail;
import com.xyl.mydelivery.mapper.OrderDetailMapper;
import com.xyl.mydelivery.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
     implements OrderDetailService {
}
