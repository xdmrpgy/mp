package com.plato.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.plato.mp.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
