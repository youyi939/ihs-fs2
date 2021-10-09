package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import com.hsgrjt.fushun.ihs.system.mapper.IhsFileMapper;
import com.hsgrjt.fushun.ihs.system.service.IhsFileService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.hsgrjt.fushun.ihs.utils.V;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: KenChen
 * @Description: 文件记录表实现类
 * @Date: Create in  2021/8/22 下午4:27
 */
@Service
public class IhsFileServiceImpl  implements IhsFileService {

    @Autowired
    IhsFileMapper ihsFileMapper;

    @Autowired
    UserService userService;

    @Override
    public IPage<IhsFile> queryList(Page<IhsFile> page, Integer id,String type) {
        QueryWrapper<IhsFile> queryWrapper = new QueryWrapper<>();
        if (!V.isEmpty(type)){
            queryWrapper.lambda().eq(IhsFile::getCategory,type);
        }
        return ihsFileMapper.selectPage(page,queryWrapper);
    }

    @Override
    public IPage<IhsFile> queryListPersonalFiles(Page<IhsFile> page, Integer id) {
        QueryWrapper<IhsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IhsFile::getCreateUserId,id);
//        queryWrapper.lambda().eq(IhsFile::getCategory,key).orderByDesc(IhsFile::getGmtCreate);
        return ihsFileMapper.selectPage(page,queryWrapper);
//        return selectPersonalList("个人文件",page,id);
    }

    @Override
    public Map<String,List<IhsFile>> queryListOtherFiles(Page<IhsFile> page) {
        return selectOtherFiles("个人文件");
    }

    @Override
    public IPage<IhsFile> queryListWeekPlan(Page<IhsFile> page, Integer id) {
        return selectPersonalList("周计划",page,id);
    }

    @Override
    public Map<String, List<IhsFile>> queryListOtherWeekPlan() {
        return selectOtherFiles("周计划");
    }

    @Override
    public IPage<IhsFile> queryListMonthPlan(Page<IhsFile> page, Integer id) {
        return selectPersonalList("月计划",page,id);
    }

    @Override
    public Map<String, List<IhsFile>> queryListOtherMonthPlan() {
        return selectOtherFiles("月计划");
    }

    /**
     * 通用查询个人文件方法
     * @param key
     * @param page
     * @param id
     * @return
     */
    public IPage<IhsFile> selectPersonalList(String key, Page<IhsFile> page, Integer id){
        QueryWrapper<IhsFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(IhsFile::getCreateUserId,id);
        queryWrapper.lambda().eq(IhsFile::getCategory,key).orderByDesc(IhsFile::getGmtCreate);
        return ihsFileMapper.selectPage(page,queryWrapper);
    }

    /**
     * 通用领导查询员工文件列表方法
     * @param key
     * @return
     */
    public Map<String, List<IhsFile>> selectOtherFiles(String key){
        List<User> userList = userService.findAllUser();
        Map<String,List<IhsFile>> map = new HashMap<String, List<IhsFile>>();
        for (User user : userList) {
            QueryWrapper<IhsFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(IhsFile::getCategory,key).eq(IhsFile::getCreateUserId,user.getId()).orderByDesc(IhsFile::getGmtCreate);
            List<IhsFile> fileList = ihsFileMapper.selectList(queryWrapper);
            map.put(user.getUsername(),fileList);
        }
        return map;
    }


    @Override
    public void save(IhsFileAddDTO entity) {

        IhsFile ihsFile  = new IhsFile();
        BeanUtils.copyProperties(entity,ihsFile);

        ihsFile.setGmtCreate(new Date());
        ihsFile.setGmtModified(new Date());

       ihsFileMapper.insert(ihsFile);
    }

    @Override
    public void updateById(IhsFile body) {

    }

    @Override
    public void removeById(Long id) {
        ihsFileMapper.deleteById(id);
    }




}
