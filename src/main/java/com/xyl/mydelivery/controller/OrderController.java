package com.xyl.mydelivery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.mydelivery.common.BaseContext;
import com.xyl.mydelivery.common.Result;
import com.xyl.mydelivery.entity.Employee;
import com.xyl.mydelivery.entity.Orders;
import com.xyl.mydelivery.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){

        log.info("订单信息:" +orders.toString());

        ordersService.submit(orders);
        return Result.success("已成功下单!");

    }
    // http://localhost:8181/order/userPage?page=1&pageSize=5
    // 分页展示订单详情
    @GetMapping("/userPage")
    public Result<Page> ordersPage(int page,int pageSize){

        Long userId = BaseContext.getCurrentId();


        //  构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();
        // 创建分页构造器对象
        Page pageInfo = new Page(page,pageSize);


        //   name不为null，才会 比较 getUsername方法和前端传入的name是否匹配 的过滤条件
//        queryWrapper.eq();
        //  根据 更新用户的时间升序 分页展示
//        queryWrapper.orderByAsc(Employee::getUpdateTime);

        // 去数据库查询
//        employeeService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }
}
