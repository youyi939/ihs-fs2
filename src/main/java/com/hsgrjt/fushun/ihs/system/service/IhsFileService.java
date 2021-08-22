package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/8/22 下午4:27
 */
@Service
public interface IhsFileService  extends SysService<IhsFile>{

    /**
     * 分页获取表列表
     * @param page
     * @param name
     * @return
     */
    IPage<IhsFile> queryList(IPage<IhsFile> page, String name);


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
