package com.xyl.mydelivery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.mydelivery.common.Result;
import com.xyl.mydelivery.dto.SetmealDto;
import com.xyl.mydelivery.entity.Category;
import com.xyl.mydelivery.entity.Setmeal;
import com.xyl.mydelivery.service.CategoryService;
import com.xyl.mydelivery.service.SetmealDishService;
import com.xyl.mydelivery.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    // 当前端传输过来的 JSON数据 与 对应实体类 Setmeal中属性有所不同时，可以使用SetmealDto，
    //   SetmealDto 继承Setmeal，并添加 Setmeal中没有的JSON数据
    @PostMapping
    public Result<String> save(@RequestBody SetmealDto setmealDto){

        log.info("套餐信息:{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return Result.success("套餐添加 成功！");
    }

    // 套餐Setmeal 分页查询
    @GetMapping("/page")
    public Result<Page> showPage(int page,int pageSize,String name){

        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        // 根据name 进行 like模糊查询
        queryWrapper.like(name != null,Setmeal::getName,name);

        setmealService.page(setmealPage,queryWrapper);

        BeanUtils.copyProperties(setmealPage,dtoPage,"records");

        List<Setmeal> records = setmealPage.getRecords();

        List<SetmealDto> dtoList = records.stream().map((record) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record, setmealDto);

            // 根据分类id查询 分类对象
            Category category = categoryService.getById(record.getCategoryId());
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(dtoList);

        return Result.success(dtoPage);
    }

    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids){

        log.info("ids = " + ids);

        setmealService.removeWithDish(ids);

        return Result.success("成功删除套餐！");
    }


}
