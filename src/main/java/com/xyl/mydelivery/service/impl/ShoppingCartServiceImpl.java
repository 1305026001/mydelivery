package com.xyl.mydelivery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.mydelivery.entity.ShoppingCart;
import com.xyl.mydelivery.mapper.ShoppingCartMapper;
import com.xyl.mydelivery.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
     implements ShoppingCartService {
}
