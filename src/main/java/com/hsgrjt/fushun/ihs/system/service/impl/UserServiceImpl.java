package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.UserMapper;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByLoginUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("username", username)
                .or().eq("email",username)
                .or().eq("mobile",username)
                .orderByDesc("gmt_modified");
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean hasUsername(String username) {
        return userMapper.hasUsername(username) > 0;
    }

    @Override
    public List<User> findAllUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean isUserVip(Integer id,String type) {
        User user = userMapper.selectById(id);
        if (user.getPermission().contains("|"+type+"|")){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public R updateUserNode(String node, Integer id) {
        userMapper.updateUserNode(node,id);
        return R.ok("更新成功");
    }
}
