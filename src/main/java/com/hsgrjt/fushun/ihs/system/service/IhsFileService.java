package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.User;
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
public interface IhsFileService  {

    /**
     * 分页获取表列表
     * @param page
     * @param id
     * @return
     */
    IPage<IhsFile> queryList(Page<IhsFile> page, Integer id, String type, User user);


    /**
     * 分页获取当前登陆人 "个人文件"列表
     * @param page
     * @param id
     * @return
     */
    IPage<IhsFile> queryListPersonalFiles(Page<IhsFile> page, Integer id);

    /**
     * 分页获取个人文件列表 领导专用
     * @param page
     * @return
     */
    Map<String,List<IhsFile>> queryListOtherFiles(Page<IhsFile> page);




    /**
     * 分页获取当前登陆人 "周计划"列表
     * @param page
     * @param id
     * @return
     */
    IPage<IhsFile> queryListWeekPlan(Page<IhsFile> page, Integer id);


    /**
     * 分页获取周计划文件列表 领导专用
     * @return
     */
    Map<String,List<IhsFile>> queryListOtherWeekPlan();



    /**
     * 分页获取当前登陆人 "月计划"列表
     * @param page
     * @param id
     * @return
     */
    IPage<IhsFile> queryListMonthPlan(Page<IhsFile> page, Integer id);


    /**
     * 分页获取月计划文件列表 领导专用
     * @return
     */
    Map<String,List<IhsFile>> queryListOtherMonthPlan();



    /**
     * 新建
     * @param entity
     */
    void save(IhsFileAddDTO entity,String company);

    /**
     * 更新字典
     * @param body
     */
    void updateById(IhsFile body);


    /**
     * 移除字典
     * @param id
     */
    void removeById(Long id);

}
