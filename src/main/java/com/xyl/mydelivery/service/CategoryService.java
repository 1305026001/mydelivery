package com.xyl.mydelivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.mydelivery.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}


