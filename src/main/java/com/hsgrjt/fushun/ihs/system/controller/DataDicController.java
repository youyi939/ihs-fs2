package com.hsgrjt.fushun.ihs.system.controller;


import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.DataDic;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.DataDicService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 *  字典表crud
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-13
 */
@Api(tags = {"CORE 字典表管理"})
@RestController
public class DataDicController {

    @Autowired
    DataDicService service;

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="新增字典")
    @PostMapping(value = "/system/dict/save")
    public R save(@RequestBody DataDic dataDic){
        service.save(dataDic);
        return R.ok("插入数据成功");
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="查询全部字典")
    @GetMapping(value = "/system/dict/findAll")
    public R findAll(@RequestParam("key")String key){
        return R.ok("查询数据成功").putData(service.findAll(key));
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="删除字典")
    @PostMapping(value = "/system/dict/delectById")
    public R save(@RequestParam("id") Long id){
        service.removeById(id);
        return R.ok("删除数据成功");
    }

}

