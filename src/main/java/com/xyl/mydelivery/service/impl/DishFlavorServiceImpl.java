package com.xyl.mydelivery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.mydelivery.entity.DishFlavor;
import com.xyl.mydelivery.mapper.DishFlavorMapper;
import com.xyl.mydelivery.mapper.DishMapper;
import com.xyl.mydelivery.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
        implements DishFlavorService {
}
