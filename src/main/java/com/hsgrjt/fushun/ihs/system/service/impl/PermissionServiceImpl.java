package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.Permission;
import com.hsgrjt.fushun.ihs.system.mapper.PermissionMapper;
import com.hsgrjt.fushun.ihs.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/9/4 下午3:04
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAllPermission() {
        return permissionMapper.selectList(new QueryWrapper<>());
    }
}
