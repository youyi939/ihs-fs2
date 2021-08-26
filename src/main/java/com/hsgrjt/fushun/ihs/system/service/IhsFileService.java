package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import org.springframework.stereotype.Service;

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
    IPage<IhsFile> queryList(Page<IhsFile> page, Integer id,String type);



    /**
     * 新建
     * @param entity
     */
    void save(IhsFileAddDTO entity);

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
