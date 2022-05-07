package com.xyl.mydelivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.mydelivery.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
