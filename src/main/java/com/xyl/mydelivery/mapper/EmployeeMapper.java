package com.xyl.mydelivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.mydelivery.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
