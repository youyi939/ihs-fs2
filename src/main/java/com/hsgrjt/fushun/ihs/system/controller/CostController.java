package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.Cost;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.CostService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 责任成本管理controller
 * @Date: Create in  2021/9/13 下午5:01
 */
@Api(tags = {"CORE 责任成本管理"})
@RestController
public class CostController {

    @Autowired
    CostService costService;

    @RequirePermission(Permissions.C_ADD)
    @ApiOperation(value="新增责任成本记录")
    @PostMapping(value = "/system/cost/save")
    public R save(@RequestBody Cost goods){
        costService.save(goods);
        return R.ok("插入数据成功");
    }

    @RequirePermission(Permissions.C_ADD)
    @ApiOperation(value="删除物料信息")
    @PostMapping(value = "/system/cost/removeByIds")
    public R updateByIds(@RequestParam("ids") List<Integer> ids){
        costService.removeByIds(ids);
        return R.ok("删除数据成功");
    }


    @RequirePermission(Permissions.C_USE)
    @ApiOperation(value="分页查询责任成本信息")
    @GetMapping(value = "/system/cost/findByPage")
    public R<IPage<Cost>> updateById(int current, int size){
        return R.ok("分页查询数据成功").putData(costService.findByPage(new Page<>(current,size)));
    }

}