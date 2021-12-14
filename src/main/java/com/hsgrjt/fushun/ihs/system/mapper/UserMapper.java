package com.hsgrjt.fushun.ihs.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsgrjt.fushun.ihs.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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


    /**
     * 更新当前登陆人的记事本
     * @param node
     * @param id
     */
    @Update("update user set note = #{note} where id = #{id};")
    void updateUserNode(@Param("note")String node , @Param("id") int id);


}
