package com.graduation.picture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.picture.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname UserMapper
 * @Date 2025/12/8 10:15
 * @Author by Eddie
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
