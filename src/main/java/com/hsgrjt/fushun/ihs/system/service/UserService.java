package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hsgrjt.fushun.ihs.system.entity.User;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
public interface UserService extends IService<User> {

    User getUserByLoginUsername(String username);

    boolean hasUsername(String username);

    List<User> findAllUser();


    Boolean isUserVip(Integer id,String type);

}
