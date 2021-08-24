package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.IhsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: KenChen
 * @Description: 文件表操作接口
 * @Date: Create in  2021/8/22 下午4:40
 */
@Api(tags = {"CORE 文件记录表操作"})
@RestController
@RequestMapping("/system/ihsFile")
public class IhsFileController {

    @Autowired
    IhsFileService fileService;

    @ApiOperation(value="新增一条文件记录")
    @PostMapping(value = "/save")
    public R save(@RequestBody IhsFileAddDTO ihsFileAddDTO){
        fileService.save(ihsFileAddDTO);
        return R.ok("插入数据成功");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "分页页数",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "size",value = "每页数据条数",dataType = "int",paramType = "query",required = true)
    })
    @ApiOperation("分页查询文件记录列表")
    @GetMapping(value = "/selectPage")
    public R<Page<IhsFile>> selectUserPage(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size,@RequestParam(name = "id") Integer id){
        return R.ok("分页查询成功").putData( fileService.queryList(new Page<IhsFile>(current,size),id));
    }


}
