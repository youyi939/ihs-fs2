package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.DataDic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsgrjt.fushun.ihs.system.entity.dto.IhsFileAddDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-13
 */
public interface DataDicService {

    /**
     * 新建
     * @param entity
     */
    void save(DataDic entity);


    /**
     * 移除字典
     * @param id
     */
    void removeById(Long id);


    /**
     * 移除字典
     */
    List<DataDic> findAll(String key);


}
