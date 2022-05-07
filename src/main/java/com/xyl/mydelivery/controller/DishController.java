package com.xyl.mydelivery.controller;


// 新增菜品:
// 1、页面发送Ajax请求，请求服务端获取菜品 的分类数据 并展示到下拉框中
// 2、页面发送请求进行图片上传，请求服务端将图片 保存到服务器
// 3、页面发送请求 进行图片下载，将上传的图片进行回显
// 4、点击保存按钮，发送Ajax请求，将菜品相关数据 以JSON形式提交到服务器

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.mydelivery.common.Result;
import com.xyl.mydelivery.dto.DishDto;
import com.xyl.mydelivery.entity.Category;
import com.xyl.mydelivery.entity.Dish;
import com.xyl.mydelivery.service.CategoryService;
import com.xyl.mydelivery.service.DishFlavorService;
import com.xyl.mydelivery.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return Result.success("新增菜品操作成功！");
    }

    //  分页展示菜品信息
    // dish/page?page=1&pageSize=10&name=122334,name 是搜索框中的输入值
    @GetMapping("/page")
    public Result<Page> pageShow(int page,int pageSize,String name){

        Page<Dish> dishPage = new Page<>(page,pageSize);
        Page<DishDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.eq(name != null,Dish::getName,name);

        //  执行分页查询
        dishService.page(dishPage,queryWrapper);

        //  将 dish 中的属性值复制到 dtoPage，但是忽略 records
        //  records需要另外去设置
        BeanUtils.copyProperties(dishPage,dtoPage,"records");

        List<Dish> records = dishPage.getRecords();
        List<DishDto> dtoList = records.stream().map((dish) -> {  // dish 为每个菜品对象
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(dish,dishDto);

            Long categoryId = dish.getCategoryId();  // 菜品的分类id

            Category category = categoryService.getById(categoryId);
           if (category != null){
               dishDto.setCategoryName(category.getName());
           }

            return dishDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(dtoList);
        return Result.success(dtoPage);
    }

    @GetMapping("/{id}")
    public Result<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByDishIdWithFlavor(id);

        return Result.success(dishDto);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return Result.success("新增菜品操作成功！");
    }

    @DeleteMapping
    public Result<String> delete(Long ids){
        dishService.removeById(ids);

        return Result.success("成功删除菜品信息！");
    }

    @GetMapping("/list")
    public Result<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        Long categoryId = dish.getCategoryId();
        queryWrapper.eq(categoryId != null,Dish::getCategoryId,categoryId);

        // status 为 1: 还在售卖的菜品
        queryWrapper.eq(Dish::getStatus,1);
        // 根据sort 属性升序片排列
        queryWrapper.orderByDesc(Dish::getSort);
        List<Dish> list = dishService.list(queryWrapper);

        return Result.success(list);
    }

    // 改变菜品的销售状态
//    @PostMapping("/status")
//    public Result<String> updateSaleStatus(@PathVariable Long ids){
//        log.info("ids :"+ids);
////        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
////        queryWrapper.eq(Dish::getId,ids);
////        dishService.update(queryWrapper);
//
//        return Result.success("菜品的售卖状态已更改！");
//
//    }

}
