package com.hsgrjt.fushun.ihs.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsgrjt.fushun.ihs.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select count(*) from user where username = #{username}")
    Integer hasUsername(String username);


}
