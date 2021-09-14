package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

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


    @ApiOperation(value="新增物料")
    @PostMapping(value = "/system/goods/save")
    public R save(@RequestBody Goods goods){
        goodsService.save(goods);
        return R.ok("插入数据成功");
    }



    @ApiOperation(value="更新物料信息")
    @PostMapping(value = "/system/goods/updateById")
    public R updateById(@RequestBody Goods goods){
        goodsService.updateById(goods);
        return R.ok("修改数据成功");
    }


    @ApiOperation(value="删除物料信息")
    @PostMapping(value = "/system/goods/removeById/{id}")
    public R updateById(@PathParam("id")Long id){
        goodsService.removeById(id);
        return R.ok("删除数据成功");
    }



    @ApiOperation(value="分页查询物料信息")
    @GetMapping(value = "/system/goods/findByPage")
    public R updateById(int current,int size){
        return R.ok("分页查询数据成功").putData(goodsService.findByPage(new Page<>(current,size)));
    }



}