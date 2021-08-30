package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.IhsFileService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: KenChen
 * @Description: 文件表操作接口
 * @Date: Create in  2021/8/22 下午4:40
 */
@Api(tags = {"CORE 文件记录表操作"})
@RestController
public class IhsFileController {

    @Autowired
    IhsFileService fileService;

    @ApiOperation(value="新增一条文件记录")
    @PostMapping(value = "/system/ihsFile/save")
    public R save(@RequestBody IhsFileAddDTO ihsFileAddDTO){
        fileService.save(ihsFileAddDTO);
        return R.ok("插入数据成功");
    }

    @RequirePermission(Permissions.F_CP)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @ApiOperation("分页查询文件记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage")
    public R<Page<IhsFile>> selectUserPage(
            @RequestParam(name = "current") Integer current,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "id") Integer id,          //当前登陆人的id
            @RequestParam(name = "type",required = false)String type){
        return R.ok("分页查询成功").putData( fileService.queryList(new Page<IhsFile>(current,size),id,type));
    }



    //查询当前登陆人的个人文件
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @RequirePermission(Permissions.S_INIT)
    @ApiOperation("分页查询当前登陆人 个人文件记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage/personalFiles")
    public R<Page<IhsFile>> selectUserPagePersonalFiles(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return R.ok("分页查询成功").putData( fileService.queryListPersonalFiles(new Page<IhsFile>(current,size),user.getId()));
    }


    //领导专用 查询员工的个人文件记录
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @RequirePermission(Permissions.S_VIP)
    @ApiOperation("分页查询其他人的 个人文件记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage/otherFiles")
    public R<Page<IhsFile>> selectOtherPagePersonalFiles(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return R.ok("分页查询成功").putData( fileService.queryListOtherFiles(new Page<IhsFile>(current,size)));
    }




    //查询当前登陆人的周计划
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @RequirePermission(Permissions.S_INIT)
    @ApiOperation("分页查询当前登陆人 周计划记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage/weekPlan")
    public R<Page<IhsFile>> selectUserPageWeekPlan(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return R.ok("分页查询成功").putData( fileService.queryListWeekPlan(new Page<IhsFile>(current,size),user.getId()));
    }


    //领导专用 查询员工的周计划文件记录
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @RequirePermission(Permissions.S_VIP)
    @ApiOperation("分页查询其他人的 周计划文件记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage/otherWeekPlan")
    public R<Page<IhsFile>> selectOtherWeekPlan( HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return R.ok("分页查询成功").putData( fileService.queryListOtherWeekPlan());
    }


    //查询当前登陆人的月计划
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @RequirePermission(Permissions.S_INIT)
    @ApiOperation("分页查询当前登陆人 月计划记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage/monthPlan")
    public R<Page<IhsFile>> selectUserPageMonthPlan(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return R.ok("分页查询成功").putData( fileService.queryListMonthPlan(new Page<IhsFile>(current,size),user.getId()));
    }


    //领导专用 查询员工的月计划文件记录
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @RequirePermission(Permissions.S_VIP)
    @ApiOperation("分页查询其他人的 月计划文件记录列表")
    @GetMapping(value = "/system/ihsFile/selectPage/otherMonthPlan")
    public R<Page<IhsFile>> selectOtherMonthPlan( HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return R.ok("分页查询成功").putData( fileService.queryListOtherMonthPlan());
    }



}
