package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.system.entity.Permission;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.IhsFileService;
import com.hsgrjt.fushun.ihs.system.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 权限查询接口
 * @Date: Create in  2021/9/4 下午3:05
 */
@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @ApiOperation(value="查询全部权限")
    @GetMapping(value = "/system/permission/findAll")
    public R<List<Permission>> findAll(){
        return R.ok("插入数据成功").putData(permissionService.findAllPermission());
    }


}
