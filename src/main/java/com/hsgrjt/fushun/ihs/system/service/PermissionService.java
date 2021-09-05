package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.Permission;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: KenChen
 * @Description: 文件记录表service
 * @Date: Create in  2021/8/22 下午4:27
 */
@Service
public interface PermissionService {

    List<Permission> findAllPermission();

}
