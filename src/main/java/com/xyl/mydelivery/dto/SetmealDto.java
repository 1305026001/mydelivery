package com.xyl.mydelivery.dto;

import com.xyl.mydelivery.entity.Setmeal;
import com.xyl.mydelivery.entity.SetmealDish;

import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
