package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.Permission;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.GoodsService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 物料管理controller
 * @Date: Create in  2021/9/13 下午5:01
 */
@Api(tags = {"CORE 物料管理"})
@RestController
public class GoodsController  {

    @Autowired
    GoodsService goodsService;


    @RequirePermission(Permissions.S_AST)
    @ApiOperation(value="新增物料")
    @PostMapping(value = "/system/goods/save")
    public R save(@RequestBody Goods goods){
        goodsService.save(goods);
        return R.ok("插入数据成功");
    }



    @RequirePermission(Permissions.S_AST)
    @ApiOperation(value="更新物料信息")
    @PostMapping(value = "/system/goods/updateById")
    public R updateById(@RequestBody Goods goods){
        goodsService.updateById(goods);
        return R.ok("修改数据成功");
    }


    @RequirePermission(Permissions.S_AST)
    @ApiOperation(value="删除物料信息")
    @PostMapping(value = "/system/goods/removeByIds")
    public R removeByIds(@RequestParam("ids") List<Integer> ids){
        goodsService.removeByIds(ids);
        return R.ok("删除数据成功");
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="分页查询物料信息")
    @GetMapping(value = "/system/goods/findByPage")
    public R findByPage(int current,int size){
        return R.ok("分页查询数据成功").putData(goodsService.findByPage(new Page<>(current,size)));
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="查询全部物料信息")
    @GetMapping(value = "/system/goods/findAll")
    public R<List<Goods>> findAll(){
        return R.ok("查询数据成功").putData(goodsService.findAll());
    }


}